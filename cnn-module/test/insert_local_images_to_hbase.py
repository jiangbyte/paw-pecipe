#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
小规模测试数据插入工具：扫描本地目录，自动将图片插入 HBase
目录结构要求：
    dataset_dir/
        ├── class1/
        │   ├── img1.jpg
        │   └── ...
        ├── class2/
        └── ...

子目录名即为标签（label）
"""

import os
import time
import random
import io
from pathlib import Path
from PIL import Image
import happybase


def generate_rowkey(label: str) -> str:
    """生成唯一 rowkey: label_timestamp_random4hex"""
    timestamp = str(int(time.time() * 1000))
    suffix = f"{random.randint(0, 65535):04x}"
    return f"{label}_{timestamp}_{suffix}"


def insert_dataset_to_hbase(dataset_dir: str, host='cluster1', port=9090, table_name='vegetable_image_data'):
    """
    扫描 dataset_dir 下的所有子目录，将图片插入 HBase。

    Args:
        dataset_dir (str): 本地数据集根目录路径
        host (str): HBase Thrift 服务器地址
        port (int): HBase Thrift 端口
        table_name (str): 目标表名
    """
    # 连接 HBase
    print(f"Connecting to HBase at {host}:{port}...")
    conn = happybase.Connection(host=host, port=port, timeout=5000)
    table = conn.table(table_name)

    # 支持的图像格式
    IMG_EXTENSIONS = {'.jpg', '.jpeg', '.png'}

    dataset_path = Path(dataset_dir)
    if not dataset_path.exists():
        raise FileNotFoundError(f"Dataset directory not found: {dataset_dir}")

    total_inserted = 0

    # 遍历每个子目录（每个子目录是一个类别）
    for class_dir in dataset_path.iterdir():
        if not class_dir.is_dir():
            continue

        label = class_dir.name
        print(f"\nProcessing class: '{label}'")

        # 遍历该类别下的所有图像文件
        for img_path in class_dir.iterdir():
            if img_path.suffix.lower() not in IMG_EXTENSIONS:
                print(f"  ⚠️ Skipping non-image file: {img_path.name}")
                continue

            try:
                # 打开并转换为 RGB（确保3通道）
                img = Image.open(img_path).convert('RGB')

                # 转为 JPEG 字节流（统一格式，减小体积）
                byte_io = io.BytesIO()
                img.save(byte_io, format='JPEG', quality=85)
                img_bytes = byte_io.getvalue()

                # 生成唯一 rowkey
                rowkey = generate_rowkey(label)

                # 构造 HBase 列数据
                data = {
                    b'img:raw': img_bytes,
                    b'meta:label': label.encode('utf-8'),
                    b'meta:source': b'local_test_tool',
                    b'meta:upload_time': str(int(time.time())).encode(),
                    b'meta:width': str(img.width).encode(),
                    b'meta:height': str(img.height).encode(),
                    b'meta:filename': img_path.name.encode('utf-8'),
                }

                # 插入 HBase
                table.put(rowkey, data)
                print(f"  ✅ Inserted: {img_path.name} -> {rowkey}")
                total_inserted += 1

            except Exception as e:
                print(f"  ❌ Failed to process {img_path}: {e}")

    conn.close()
    print(f"\n🎉 Done! Total {total_inserted} images inserted into table '{table_name}'.")


if __name__ == "__main__":
    dataset_dir = "IMAGES_TRAIN"
    host = 'cluster1'
    port = 9090
    table = 'vegetable_image_data'

    insert_dataset_to_hbase(
        dataset_dir=dataset_dir,
        host=host,
        port=port,
        table_name=table
    )
