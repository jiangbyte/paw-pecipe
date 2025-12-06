# train.py
import torch
import torch.nn as nn
import torch.optim as optim
import torch.profiler
from torch.utils.data import DataLoader, Subset
import torchvision.transforms as transforms
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
import matplotlib.pyplot as plt
import json
import os

from vegetable_dataset import VegetableDataset
from vegetable_model import VegetableCNN


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
            # all_labels.extend(labels.cpu().numpy())
            # all_preds.extend(predicted.cpu().numpy())
            # 直接用 .tolist() 避免 .numpy() 的额外拷贝
            all_labels.extend(labels.tolist())
            all_preds.extend(predicted.tolist())
    acc = correct / total
    return acc, all_labels, all_preds


if __name__ == '__main__':
    DATA_ROOT = "../test/IMAGES_TRAIN"
    BATCH_SIZE = 16
    EPOCHS = 50
    PATIENCE = 10
    MIN_DELTA = 0.001
    NUM_WORKERS = min(4, os.cpu_count())

    # === 数据预处理 ===
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

    # === 步骤1：加载完整数据集以构建标签映射（不加 transform）===
    print("正在加载数据集元信息...")
    full_dataset_raw = VegetableDataset(root=DATA_ROOT, transform=None)
    LABEL_TO_IDX = full_dataset_raw.label_to_idx
    IDX_TO_LABEL = full_dataset_raw.idx_to_label
    num_classes = len(LABEL_TO_IDX)

    print("检测到的类别映射:")
    for label, idx in LABEL_TO_IDX.items():
        print(f"  {label} -> {idx}")
    print(f"共 {num_classes} 个类别\n")

    # === 步骤2：划分训练/测试索引（互斥且固定种子）===
    total_size = len(full_dataset_raw)
    train_size = int(0.8 * total_size)
    test_size = total_size - train_size

    generator = torch.Generator().manual_seed(42)
    indices = torch.randperm(total_size, generator=generator).tolist()
    train_indices = indices[:train_size]
    test_indices = indices[train_size:]

    # === 步骤3：创建带 transform 的 dataset（共享相同 label_to_idx 和样本顺序）===
    print("正在构建训练/测试数据集（含预加载）...")
    train_dataset = VegetableDataset(
        root=DATA_ROOT,
        transform=train_transform,
        label_to_idx=LABEL_TO_IDX
    )
    test_dataset = VegetableDataset(
        root=DATA_ROOT,
        transform=test_transform,
        label_to_idx=LABEL_TO_IDX
    )

    # === 步骤4：创建 Subset 和 DataLoader ===
    train_subset = Subset(train_dataset, train_indices)
    test_subset = Subset(test_dataset, test_indices)

    train_loader = DataLoader(train_subset, batch_size=BATCH_SIZE, shuffle=True, num_workers=NUM_WORKERS,
                              pin_memory=False)
    test_loader = DataLoader(test_subset, batch_size=BATCH_SIZE, shuffle=False, num_workers=NUM_WORKERS,
                             pin_memory=False)

    print(f"训练集样本数: {len(train_subset)}")
    print(f"测试集样本数: {len(test_subset)}")

    # === 模型 & 训练 ===
    device = torch.device("cpu")
    model = VegetableCNN(num_classes=num_classes)
    optimizer = optim.Adam(model.parameters(), lr=0.001, weight_decay=1e-4)
    loss_fn = nn.CrossEntropyLoss()

    best_acc = 0.0
    patience_counter = 0
    train_losses, train_accuracies, test_accuracies = [], [], []

    print("\n开始训练...\n")

    for epoch in range(EPOCHS):
        model.train()
        running_loss = 0.0
        for images, labels in train_loader:
            optimizer.zero_grad()
            outputs = model(images)
            loss = loss_fn(outputs, labels)
            loss.backward()
            optimizer.step()
            running_loss += loss.item() * images.size(0)

        epoch_loss = running_loss / len(train_subset)
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
            os.makedirs("result_model", exist_ok=True)
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
            print(f"⏹️ 早停触发，连续 {PATIENCE} 个 epoch 没有提升")
            break

    print(f"\n训练完成！最佳测试准确率: {best_acc:.4f}")

    # === 最终评估与绘图 ===
    device = torch.device("cpu")
    final_model = VegetableCNN(num_classes=num_classes)
    state_dict = torch.load("result_model/CNN_Vegetable_Model_best.pt", weights_only=False, map_location=device)
    final_model.load_state_dict(state_dict)

    final_acc, final_labels, final_preds = evaluate(final_model, test_loader)
    print(f"✅ 最终测试准确率: {final_acc:.4f}")

    os.makedirs("result_images", exist_ok=True)
    plt.figure(figsize=(16, 6))

    plt.subplot(1, 3, 1)
    plt.plot(train_losses, color='tab:red', linewidth=2)
    plt.title('Training Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.grid(True)

    plt.subplot(1, 3, 2)
    plt.plot(train_accuracies, label='Train Acc', color='tab:green', linewidth=2)
    plt.plot(test_accuracies, label='Test Acc', color='tab:blue', linewidth=2)
    plt.title('Accuracy Curves')
    plt.xlabel('Epoch')
    plt.ylabel('Accuracy')
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
    print("✅ 训练分析图表已保存为: result_images/training_analysis.png")

    # === 导出 ONNX ===
    dummy_input = torch.randn(1, 3, 224, 224)
    torch.onnx.export(
        final_model,
        dummy_input,
        "result_model/vegetable_classifier.onnx",
        export_params=True,
        opset_version=13,
        do_constant_folding=True,
        input_names=['input'],
        output_names=['output'],
        dynamic_axes={
            'input': {0: 'batch_size', 2: 'height', 3: 'width'},
            'output': {0: 'batch_size'}
        }
    )
    print("✅ 模型已成功导出为 ONNX 格式: result_model/vegetable_classifier.onnx")

    print("\n保存的文件列表:")
    print("- result_model/CNN_Vegetable_Model_best.pt")
    print("- result_model/label_to_idx.json")
    print("- result_model/idx_to_label.json")
    print("- result_model/vegetable_classifier.onnx")
    print("- result_images/training_analysis.png")

    print("\n" + "=" * 60)
    print("模型部署说明:")
    print("1. ONNX模型支持动态输入尺寸 (batch_size, channels, height, width)")
    print("2. 推理时请先将图像 Resize 到 224x224 并归一化（与训练一致）")
    print("3. 标签映射见 label_to_idx.json")
    print("=" * 60)
