# vegetable_dataset.py
import os
from PIL import Image
from torch.utils.data import Dataset
from typing import Optional, Callable, List, Tuple


class VegetableDataset(Dataset):
    """
    从本地文件系统加载蔬菜图像数据集。
    要求目录结构：
        root/
          ├── class1/
          │   ├── img1.jpg
          │   └── ...
          ├── class2/
          └── ...

    自动构建标签映射。为确保可复现性，所有文件和类别均按名称排序。
    """

    def __init__(
            self,
            root: str,
            transform: Optional[Callable] = None,
            label_to_idx: Optional[dict] = None
    ):
        self.root = root
        self.transform = transform
        self.samples: List[Tuple[str, int]] = []  # (image_path, label_id)

        # 获取所有类别子目录（排序）
        classes = sorted([d for d in os.listdir(root) if os.path.isdir(os.path.join(root, d))])
        if not classes:
            raise ValueError(f"未在 {root} 中找到任何类别子目录")

        # 构建或使用传入的标签映射
        if label_to_idx is None:
            self.label_to_idx = {cls_name: idx for idx, cls_name in enumerate(classes)}
        else:
            self.label_to_idx = label_to_idx

        # 遍历每个类别，收集图像路径（文件名也排序！）
        for class_name in classes:
            if class_name not in self.label_to_idx:
                continue  # 跳过未知类别
            class_dir = os.path.join(root, class_name)
            label_id = self.label_to_idx[class_name]
            # 👇 关键：对文件名排序，确保顺序固定
            fnames = sorted(os.listdir(class_dir))
            for fname in fnames:
                if fname.lower().endswith(('.png', '.jpg', '.jpeg', '.bmp', '.tiff')):
                    self.samples.append((os.path.join(class_dir, fname), label_id))

        if len(self.samples) == 0:
            raise ValueError(f"未在 {root} 中找到任何有效图像文件")

        self.idx_to_label = {v: k for k, v in self.label_to_idx.items()}

    def __len__(self) -> int:
        return len(self.samples)

    def __getitem__(self, index) -> Tuple:
        img_path, label_id = self.samples[index]
        try:
            image = Image.open(img_path).convert("RGB")
        except Exception as e:
            raise RuntimeError(f"无法加载图像 {img_path}: {e}")

        if self.transform is not None:
            image = self.transform(image)

        return image, label_id
