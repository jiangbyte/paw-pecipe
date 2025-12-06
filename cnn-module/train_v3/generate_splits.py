import happybase
import json
from sklearn.model_selection import train_test_split
import os


def scan_and_split(
        host="cluster1",
        port=9090,
        table_name="vegetable_image_data",
        test_size=0.2,
        random_state=42,
        output_dir="data_splits"
):
    os.makedirs(output_dir, exist_ok=True)

    print("正在从 HBase 扫描所有 row_key 和 label（仅元数据）...")
    conn = happybase.Connection(host=host, port=port)
    table = conn.table(table_name)

    row_keys = []
    labels = []

    for key, data in table.scan(columns=[b"meta:label"]):
        label = data[b"meta:label"].decode("utf-8")
        row_keys.append(key.decode("utf-8"))
        labels.append(label)

    conn.close()

    print(f"共扫描到 {len(row_keys)} 条记录")

    train_keys, test_keys, train_labels, test_labels = train_test_split(
        row_keys, labels, test_size=test_size, random_state=random_state, stratify=labels
    )

    # 保存 keys 和 labels 分别为文本文件（每行一个）
    with open(f"{output_dir}/train_keys.txt", "w") as f:
        f.write("\n".join(train_keys))
    with open(f"{output_dir}/train_labels.txt", "w") as f:
        f.write("\n".join(train_labels))
    with open(f"{output_dir}/test_keys.txt", "w") as f:
        f.write("\n".join(test_keys))
    with open(f"{output_dir}/test_labels.txt", "w") as f:
        f.write("\n".join(test_labels))

    unique_labels = sorted(set(labels))
    label_to_idx = {label: idx for idx, label in enumerate(unique_labels)}
    with open(f"{output_dir}/label_to_idx.json", "w", encoding="utf-8") as f:
        json.dump(label_to_idx, f, ensure_ascii=False, indent=2)

    print(f"✅ 划分完成！保存至 {output_dir}/")
    print(f"   训练集: {len(train_keys)} | 测试集: {len(test_keys)}")


if __name__ == "__main__":
    scan_and_split()
