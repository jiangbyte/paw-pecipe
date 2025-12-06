import happybase
import io
from PIL import Image
from torch.utils.data import Dataset
from typing import List, Tuple, Optional, Callable


class VegetableDataset(Dataset):
    """
    从 HBase 表 'vegetable_image_data' 加载蔬菜图像数据集。
    要求表结构：
      - 列族 'img'：包含 'raw' 列（JPEG/PNG 原始字节）
      - 列族 'meta'：包含 'label' 列（字符串标签）
    """

    def __init__(
            self,
            row_keys: List[str],  # HBase 行键列表
            labels: List[str],  # 每个样本的标签字符串
            label_to_idx: dict,  # 标签到类别ID的映射
            host: str = "cluster1",  # HBase Thrift 服务器主机
            port: int = 9090,  # HBase Thrift 端口
            table_name: str = "vegetable_image_data",  # 表名
            transform: Optional[Callable] = None,  # 图像变换函数
    ):
        # 确保 row_keys 和 labels 长度一致
        assert len(row_keys) == len(labels), "row_keys 和 labels 长度必须相同"
        self.row_keys = row_keys
        self.labels = labels
        self.label_to_idx = label_to_idx
        self.transform = transform

        # 建立 HBase 连接
        try:
            self._conn = happybase.Connection(host=host, port=port, timeout=5000)
            self._table = self._conn.table(table_name)
        except Exception as e:
            raise RuntimeError(f"无法连接 HBase: {e}")

    def __len__(self) -> int:
        # 返回数据集大小
        return len(self.row_keys)

    def __getitem__(self, index) -> Tuple:
        # 获取指定索引的样本
        row_key = self.row_keys[index]
        label_str = self.labels[index]

        # 从 HBase 获取图像原始字节
        data = self._table.row(row_key.encode(), columns=[b"img:raw"])
        img_bytes = data.get(b"img:raw")
        if img_bytes is None:
            raise ValueError(f"Row key '{row_key}' 缺少图像数据")

        # 将字节流解码为 PIL 图像
        try:
            image = Image.open(io.BytesIO(img_bytes)).convert("RGB")
        except Exception as e:
            raise ValueError(f"无法解码图像 (row={row_key}): {e}")

        # 应用图像变换（如 Resize, ToTensor 等）
        if self.transform is not None:
            image = self.transform(image)

        # 将标签字符串转换为整数类别ID
        if label_str not in self.label_to_idx:
            raise KeyError(f"未知标签: {label_str}")
        label_id = self.label_to_idx[label_str]

        # 返回图像张量和标签ID
        return image, label_id

    def __del__(self):
        """尝试关闭 HBase 连接"""
        try:
            if hasattr(self, '_conn') and self._conn:
                self._conn.close()
        except:
            pass
