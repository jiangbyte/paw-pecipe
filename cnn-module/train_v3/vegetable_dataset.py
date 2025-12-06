# vegetable_dataset.py
import happybase
import io
from PIL import Image
from torch.utils.data import Dataset
import json
import os

# 使用进程 ID 作为 key，实现 per-process connection
_CONNECTION_CACHE = {}


class VegetableDataset(Dataset):
    def __init__(
            self,
            key_file: str,
            label_file: str,
            label_to_idx_file: str,
            host: str = "cluster1",
            port: int = 9090,
            table_name: str = "vegetable_image_data",
            transform=None,
    ):
        with open(key_file, "r") as f:
            self.row_keys = [line.strip() for line in f if line.strip()]
        with open(label_file, "r") as f:
            self.labels = [line.strip() for line in f if line.strip()]
        with open(label_to_idx_file, "r", encoding="utf-8") as f:
            self.label_to_idx = json.load(f)

        self.host = host
        self.port = port
        self.table_name = table_name
        self.transform = transform

    def _get_table(self):
        pid = os.getpid()
        if pid not in _CONNECTION_CACHE:
            # 每个进程只创建一次连接
            conn = happybase.Connection(
                host=self.host,
                port=self.port,
                timeout=10000
            )
            table = conn.table(self.table_name)
            _CONNECTION_CACHE[pid] = (conn, table)
        return _CONNECTION_CACHE[pid][1]

    def __len__(self):
        return len(self.row_keys)

    def __getitem__(self, index):
        row_key = self.row_keys[index]
        label_str = self.labels[index]

        table = self._get_table()
        data = table.row(row_key.encode(), columns=[b"img:raw"])
        img_bytes = data.get(b"img:raw")
        if img_bytes is None:
            raise ValueError(f"Missing image for key: {row_key}")

        image = Image.open(io.BytesIO(img_bytes)).convert("RGB")
        if self.transform:
            image = self.transform(image)

        label_id = self.label_to_idx[label_str]
        return image, label_id
