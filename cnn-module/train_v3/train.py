import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader
import torchvision.transforms as transforms
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
import matplotlib.pyplot as plt
import json
import os

from vegetable_dataset import VegetableDataset
from vegetable_model import VegetableCNN

# === 配置、函数定义等 ===
DATA_DIR = "data_splits"
BATCH_SIZE = 32
NUM_WORKERS = 4
EPOCHS = 50
PATIENCE = 10
MIN_DELTA = 0.001


def evaluate(model, loader):
    model.eval()
    correct = 0
    total = 0
    all_labels = []
    all_preds = []
    with torch.no_grad():
        for images, labels in loader:
            outputs = model(images)
            _, predicted = torch.max(outputs, 1)
            total += labels.size(0)
            correct += (predicted == labels).sum().item()
            all_labels.extend(labels.cpu().numpy())
            all_preds.extend(predicted.cpu().numpy())
    acc = correct / total
    return acc, all_labels, all_preds


# 所有“执行”逻辑必须放在 if __name__ == '__main__': 内部
if __name__ == '__main__':
    # === 加载标签映射 ===
    with open(os.path.join(DATA_DIR, "label_to_idx.json"), "r", encoding="utf-8") as f:
        LABEL_TO_IDX = json.load(f)
    IDX_TO_LABEL = {v: k for k, v in LABEL_TO_IDX.items()}
    num_classes = len(LABEL_TO_IDX)

    print("检测到的类别映射:")
    for label, idx in LABEL_TO_IDX.items():
        print(f"  {label} -> {idx}")
    print(f"共 {num_classes} 个类别\n")

    # === 数据增强 ===
    train_transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.RandomHorizontalFlip(),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])

    test_transform = transforms.Compose([
        transforms.Resize((224, 224)),
        transforms.ToTensor(),
        transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    ])

    # === 先测试能否读取一条数据 ===
    print("测试 HBase 单条读取...")
    test_dataset_debug = VegetableDataset(
        key_file=os.path.join(DATA_DIR, "train_keys.txt"),
        label_file=os.path.join(DATA_DIR, "train_labels.txt"),
        label_to_idx_file=os.path.join(DATA_DIR, "label_to_idx.json"),
        transform=test_transform
    )
    try:
        img, lbl = test_dataset_debug[0]
        print(f"成功读取第0条数据: {img.size}, label={lbl}")
    except Exception as e:
        print(f"读取失败: {e}")
        exit(1)

    # === 创建 Dataset ===
    train_dataset = VegetableDataset(
        key_file=os.path.join(DATA_DIR, "train_keys.txt"),
        label_file=os.path.join(DATA_DIR, "train_labels.txt"),
        label_to_idx_file=os.path.join(DATA_DIR, "label_to_idx.json"),
        transform=train_transform
    )

    test_dataset = VegetableDataset(
        key_file=os.path.join(DATA_DIR, "test_keys.txt"),
        label_file=os.path.join(DATA_DIR, "test_labels.txt"),
        label_to_idx_file=os.path.join(DATA_DIR, "label_to_idx.json"),
        transform=test_transform
    )

    # 启用多进程
    train_loader = DataLoader(
        train_dataset,
        batch_size=BATCH_SIZE,
        shuffle=True,
        num_workers=NUM_WORKERS,
        pin_memory=True
        # persistent_workers=True
    )
    test_loader = DataLoader(
        test_dataset,
        batch_size=BATCH_SIZE,
        shuffle=False,
        num_workers=NUM_WORKERS,
        pin_memory=True
        # persistent_workers=True
    )

    print(f"训练集样本数: {len(train_dataset)}")
    print(f"测试集样本数: {len(test_dataset)}")

    # === 模型与优化器 ===
    model = VegetableCNN(num_classes=num_classes)
    optimizer = optim.Adam(model.parameters(), lr=0.001, weight_decay=1e-4)
    loss_fn = nn.CrossEntropyLoss()

    # === 训练循环 ===
    best_acc = 0.0
    patience_counter = 0
    train_losses, train_accuracies, test_accuracies = [], [], []

    os.makedirs("result_model", exist_ok=True)
    os.makedirs("result_images", exist_ok=True)

    print("\n开始训练...\n")

    print("开始第一个 epoch...")
    for epoch in range(EPOCHS):
        print(f"Epoch {epoch + 1} 开始...")
        model.train()
        batch_count = 0
        running_loss = 0.0
        for images, labels in train_loader:
            optimizer.zero_grad()
            outputs = model(images)
            loss = loss_fn(outputs, labels)
            loss.backward()
            optimizer.step()
            running_loss += loss.item() * images.size(0)
            batch_count += 1
            if batch_count == 1:
                print(f"成功加载第1个 batch: images.shape={images.shape}")

        epoch_loss = running_loss / len(train_dataset)
        train_acc, _, _ = evaluate(model, train_loader)
        test_acc, _, _ = evaluate(model, test_loader)

        train_losses.append(epoch_loss)
        train_accuracies.append(train_acc)
        test_accuracies.append(test_acc)

        print(f"Epoch {epoch + 1}/{EPOCHS} - "
              f"Train Loss: {epoch_loss:.4f} - "
              f"Train Acc: {train_acc:.4f} - "
              f"Test Acc: {test_acc:.4f}")

        if test_acc > best_acc + MIN_DELTA:
            best_acc = test_acc
            torch.save(model.state_dict(), "result_model/CNN_Vegetable_Model_best.pt")
            with open("result_model/label_to_idx.json", "w", encoding="utf-8") as f:
                json.dump(LABEL_TO_IDX, f, ensure_ascii=False, indent=2)
            with open("result_model/idx_to_label.json", "w", encoding="utf-8") as f:
                json.dump(IDX_TO_LABEL, f, ensure_ascii=False, indent=2)
            patience_counter = 0
            print(f"✅ 新最佳模型已保存，准确率: {best_acc:.4f}")
        else:
            patience_counter += 1

        if patience_counter >= PATIENCE:
            print(f"⏹️ 早停触发，连续 {PATIENCE} 个 epoch 无提升")
            break

    # === 最终评估与绘图 ===
    device = torch.device("cpu")
    final_model = VegetableCNN(num_classes=num_classes)
    state_dict = torch.load("result_model/CNN_Vegetable_Model_best.pt", weights_only=True, map_location=device)
    final_model.load_state_dict(state_dict)

    final_acc, final_labels, final_preds = evaluate(final_model, test_loader)
    print(f"\n✅ 最终测试准确率: {final_acc:.4f}")

    plt.figure(figsize=(16, 6))
    plt.subplot(1, 3, 1)
    plt.plot(train_losses, color='tab:red')
    plt.title('Training Loss')
    plt.grid(True)

    plt.subplot(1, 3, 2)
    plt.plot(train_accuracies, label='Train', color='green')
    plt.plot(test_accuracies, label='Test', color='blue')
    plt.title('Accuracy')
    plt.legend()
    plt.grid(True)

    plt.subplot(1, 3, 3)
    cm = confusion_matrix(final_labels, final_preds)
    disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=list(LABEL_TO_IDX.keys()))
    disp.plot(cmap='Blues', ax=plt.gca())
    plt.xticks(rotation=45, ha='right')
    plt.title('Confusion Matrix')

    plt.tight_layout()
    plt.savefig("result_images/training_analysis.png", dpi=300, bbox_inches='tight')
    print("✅ 训练分析图已保存")

    # === 导出 ONNX ===
    dummy_input = torch.randn(1, 3, 224, 224)
    torch.onnx.export(
        final_model, dummy_input,
        "result_model/vegetable_classifier.onnx",
        export_params=True,
        opset_version=13,
        do_constant_folding=True,
        input_names=['input'],
        output_names=['output'],
        dynamic_axes={'input': {0: 'batch_size'}, 'output': {0: 'batch_size'}}
    )
    print("✅ ONNX 模型已导出")

    print("\n" + "=" * 60)
    print("训练完成！所有结果已保存。")
    print("=" * 60)
