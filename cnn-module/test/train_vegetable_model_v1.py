import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader
import torchvision.transforms as transforms
from sklearn.model_selection import train_test_split
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
import happybase
import matplotlib.pyplot as plt
from v2.vegetable_dataset import VegetableDataset
from v2.vegetable_model import VegetableCNN


def load_all_samples(host="cluster1", port=9090, table_name="vegetable_image_data"):
    conn = happybase.Connection(host=host, port=port)
    table = conn.table(table_name)

    row_keys = []
    labels = []
    unique_labels = set()

    for key, data in table.scan(columns=[b"meta:label"]):
        label = data[b"meta:label"].decode("utf-8")
        unique_labels.add(label)
        row_keys.append(key.decode("utf-8"))
        labels.append(label)

    conn.close()

    sorted_labels = sorted(unique_labels)
    label_to_idx = {label: idx for idx, label in enumerate(sorted_labels)}
    return row_keys, labels, label_to_idx


# --- 主程序开始 ---

print("正在从 HBase 加载数据并自动构建类别映射...")
all_keys, all_labels, LABEL_TO_IDX = load_all_samples()

print("检测到的类别映射:")
for label, idx in LABEL_TO_IDX.items():
    print(f"  {label} -> {idx}")

IDX_TO_LABEL = {v: k for k, v in LABEL_TO_IDX.items()}
num_classes = len(LABEL_TO_IDX)
print(f"共 {num_classes} 个类别\n")

train_keys, test_keys, train_labels, test_labels = train_test_split(
    all_keys, all_labels, test_size=0.2, random_state=42, stratify=all_labels
)

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

train_dataset = VegetableDataset(
    row_keys=train_keys,
    labels=train_labels,
    label_to_idx=LABEL_TO_IDX,
    transform=train_transform
)
test_dataset = VegetableDataset(
    row_keys=test_keys,
    labels=test_labels,
    label_to_idx=LABEL_TO_IDX,
    transform=test_transform
)

train_loader = DataLoader(train_dataset, batch_size=16, shuffle=True, num_workers=0)
test_loader = DataLoader(test_dataset, batch_size=16, shuffle=False, num_workers=0)

print(f"训练集样本数: {len(train_dataset)}")
print(f"测试集样本数: {len(test_dataset)}")

model = VegetableCNN(num_classes=num_classes)
optimizer = optim.Adam(model.parameters(), lr=0.001, weight_decay=1e-4)
loss_fn = nn.CrossEntropyLoss()


# 改进的评估函数：返回准确率 + 真实/预测标签
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


# === 训练配置 ===
EPOCHS = 100
best_acc = 0.0
patience = 40
min_delta = 0.001  # 最小提升阈值
patience_counter = 0

# === 记录指标 ===
train_losses = []
train_accuracies = []
test_accuracies = []

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

    epoch_loss = running_loss / len(train_dataset)

    # 评估训练集准确率（稍慢）
    train_acc, _, _ = evaluate(model, train_loader)
    # 评估测试集
    test_acc, _, _ = evaluate(model, test_loader)

    # 记录
    train_losses.append(epoch_loss)
    train_accuracies.append(train_acc)
    test_accuracies.append(test_acc)

    print(f"Epoch {epoch + 1}/{EPOCHS} - "
          f"Train Loss: {epoch_loss:.4f} - "
          f"Train Acc: {train_acc:.4f} - "
          f"Test Acc: {test_acc:.4f}")

    # 保存最佳模型（基于测试准确率）
    if test_acc > best_acc + min_delta:
        best_acc = test_acc
        torch.save(model.state_dict(), "result_model/CNN_Vegetable_Model_best.pt")
        patience_counter = 0
        print(f"✅ 新最佳模型已保存，准确率: {best_acc:.4f}")
    else:
        patience_counter += 1

    if patience_counter >= patience:
        print(f"⏹️ 早停触发，连续 {patience} 个 epoch 没有提升")
        break

print(f"\n训练完成！最佳测试准确率: {best_acc:.4f}")

# === 最终评估、绘图 ===
device = torch.device("cpu")
final_model = VegetableCNN(num_classes=num_classes)
state_dict = torch.load("result_model/CNN_Vegetable_Model_best.pt", weights_only=True, map_location=device)
final_model.load_state_dict(state_dict)
final_model.eval()

# 获取最终测试结果用于混淆矩阵
final_acc, final_labels, final_preds = evaluate(final_model, test_loader)
print(f"✅ 最终测试准确率: {final_acc:.4f}")

# === 绘制综合分析图 ===
plt.figure(figsize=(16, 6))

# 图1: 损失曲线
plt.subplot(1, 3, 1)
plt.plot(train_losses, color='tab:red', linewidth=2)
plt.title('Training Loss')
plt.xlabel('Epoch')
plt.ylabel('Loss')
plt.grid(True)

# 图2: 准确率曲线
plt.subplot(1, 3, 2)
plt.plot(train_accuracies, label='Train Acc', color='tab:green', linewidth=2)
plt.plot(test_accuracies, label='Test Acc', color='tab:blue', linewidth=2)
plt.title('Accuracy Curves')
plt.xlabel('Epoch')
plt.ylabel('Accuracy')
plt.legend()
plt.grid(True)

# 图3: 混淆矩阵
plt.subplot(1, 3, 3)
cm = confusion_matrix(final_labels, final_preds)
disp = ConfusionMatrixDisplay(confusion_matrix=cm, display_labels=list(LABEL_TO_IDX.keys()))
disp.plot(cmap='Blues', ax=plt.gca())
plt.xticks(rotation=45, ha='right')
plt.title('Confusion Matrix')

plt.tight_layout()
plt.savefig("result_images/training_analysis.png", dpi=300, bbox_inches='tight')
print("✅ 训练分析图表已保存为: result_images/training_analysis.png")

# ==============================
# 🚀 导出 ONNX 模型
# ==============================
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
        'input': {0: 'batch_size'},
        'output': {0: 'batch_size'}
    }
)
print("✅ 模型已成功导出为 ONNX 格式: result_model/vegetable_classifier.onnx")
