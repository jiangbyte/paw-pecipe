import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import DataLoader
import torchvision.transforms as transforms
from sklearn.model_selection import train_test_split
import happybase
from v2.vegetable_dataset import VegetableDataset
from v2.vegetable_model import VegetableCNN

import matplotlib.pyplot as plt

# 设置matplotlib的中文字体为楷体，解决中文显示问题
plt.rcParams['font.sans-serif']=['Simsun']
# 设置matplotlib的负号正常显示，避免负号显示为方块
plt.rcParams['axes.unicode_minus']=False

def load_all_samples(host="cluster1", port=9090, table_name="vegetable_image_data"):
    """
    从 HBase 表中扫描所有样本，并自动构建 label_to_idx 映射。

    返回:
        row_keys: 所有样本的 row key (字符串列表)
        labels:   每个样本对应的标签字符串
        label_to_idx: 标签到类别 ID 的映射字典
    """
    conn = happybase.Connection(host=host, port=port)
    table = conn.table(table_name)

    row_keys = []
    labels = []
    unique_labels = set()

    # 扫描所有行，仅获取 meta:label 列以提高效率
    for key, data in table.scan(columns=[b"meta:label"]):
        label = data[b"meta:label"].decode("utf-8")
        unique_labels.add(label)
        row_keys.append(key.decode("utf-8"))
        labels.append(label)

    conn.close()

    # 按字母顺序排序，保证映射一致性和可复现性
    sorted_labels = sorted(unique_labels)
    label_to_idx = {label: idx for idx, label in enumerate(sorted_labels)}

    return row_keys, labels, label_to_idx


# --- 主程序开始 ---

# 1. 自动从 HBase 加载数据并构建类别映射
print("正在从 HBase 加载数据并自动构建类别映射...")
all_keys, all_labels, LABEL_TO_IDX = load_all_samples()

print("检测到的类别映射:")
for label, idx in LABEL_TO_IDX.items():
    print(f"  {label} -> {idx}")

IDX_TO_LABEL = {v: k for k, v in LABEL_TO_IDX.items()}
num_classes = len(LABEL_TO_IDX)
print(f"共 {num_classes} 个类别\n")

# 2. 划分训练集和测试集（80%训练，20%测试）
# 使用 stratify=all_labels 确保各类别比例在训练/测试集中一致
train_keys, test_keys, train_labels, test_labels = train_test_split(
    all_keys, all_labels, test_size=0.2, random_state=42, stratify=all_labels
)

# 3. 定义图像预处理和数据增强
# 训练集：包含数据增强
train_transform = transforms.Compose([
    transforms.Resize((224, 224)),  # 统一输入尺寸
    transforms.RandomHorizontalFlip(),  # 随机水平翻转，增强泛化
    transforms.ToTensor(),  # 转为 [0,1] 张量
    # ImageNet 预训练模型的标准化参数，合理复用
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])

# 测试集：仅 Resize 和 Normalize，不增强
test_transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])

# 4. 创建训练和测试数据集
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

# 5. 创建 DataLoader
# batch_size=16：合理，内存友好
# shuffle=True：训练时打乱
# num_workers=0：在 Windows 或某些环境中避免多进程问题，生产环境可设为 >0
train_loader = DataLoader(train_dataset, batch_size=16, shuffle=True, num_workers=0)
test_loader = DataLoader(test_dataset, batch_size=16, shuffle=False, num_workers=0)

print(f"训练集样本数: {len(train_dataset)}")
print(f"测试集样本数: {len(test_dataset)}")

# 6. 初始化模型、优化器和损失函数
model = VegetableCNN(num_classes=num_classes)
# Adam 优化器，学习率 0.001，weight_decay=1e-4 防止过拟合
optimizer = optim.Adam(model.parameters(), lr=0.001, weight_decay=1e-4)
# CrossEntropyLoss：适用于多分类，输入为 logits（未归一化），正确
loss_fn = nn.CrossEntropyLoss()


# 7. 定义评估函数
def evaluate(model, loader):
    model.eval()  # 设置为评估模式
    correct = 0
    total = 0
    with torch.no_grad():  # 不计算梯度，节省内存
        for images, labels in loader:
            outputs = model(images)
            _, predicted = torch.max(outputs, 1)  # 获取预测类别
            total += labels.size(0)
            correct += (predicted == labels).sum().item()
    return correct / total  # 返回准确率


# 8. 训练过程（带早停机制）
EPOCHS = 100
best_acc = 0.0
patience = 20  # 早停耐心值
patience_counter = 0

print("\n开始训练...\n")

for epoch in range(EPOCHS):
    model.train()  # 设置为训练模式
    running_loss = 0.0
    for images, labels in train_loader:
        optimizer.zero_grad()  # 梯度清零
        outputs = model(images)  # 前向传播
        loss = loss_fn(outputs, labels)  # 计算损失
        loss.backward()  # 反向传播
        optimizer.step()  # 更新参数
        # 累计损失（加权平均）
        running_loss += loss.item() * images.size(0)

    # 计算整个 epoch 的平均损失
    epoch_loss = running_loss / len(train_dataset)
    # 在测试集上评估准确率
    test_acc = evaluate(model, test_loader)

    print(f"轮次(Epoch) {epoch + 1}/{EPOCHS} - 损失值(Loss): {epoch_loss:.4f} - 测试准确率(Test Acc): {test_acc:.4f}")

    # 保存最佳模型
    if test_acc > best_acc:
        best_acc = test_acc
        torch.save(model.state_dict(), "../CNN_Vegetable_Model_best.pt")
        patience_counter = 0
        print(f"✅ 新最佳模型已保存，准确率: {best_acc:.4f}")
    else:
        patience_counter += 1

    # 早停判断
    if patience_counter >= patience:
        print(f"⏹️ 早停触发，连续 {patience} 个 epoch 没有提升")
        break

print(f"\n训练完成！最佳测试准确率: {best_acc:.4f}")

# 训练结束后，加载最佳模型再评估一次
model.load_state_dict(torch.load("../CNN_Vegetable_Model_best.pt", weights_only=False))
final_acc = evaluate(model, test_loader)
print(f"✅ 最终测试准确率: {final_acc:.4f}")

# ==============================
# 🚀 导出模型为 ONNX 格式
# ==============================

import torch.onnx

# 1. 加载训练好的模型权重（安全方式）
device = torch.device("cpu")  # ONNX 通常用 CPU 导出，避免 GPU 张量问题
model = VegetableCNN(num_classes=num_classes)
state_dict = torch.load("../CNN_Vegetable_Model_best.pt", weights_only=True, map_location=device)
model.load_state_dict(state_dict)
model.eval()  # eval 模式！

# 2. 创建一个 dummy input（模拟真实输入）
# 注意：尺寸必须和训练时一致 (batch_size=1, channels=3, height=224, width=224)
dummy_input = torch.randn(1, 3, 224, 224, device=device)

# 3. 导出为 ONNX
onnx_file_path = "../vegetable_classifier.onnx"
torch.onnx.export(
    model,
    dummy_input,
    onnx_file_path,
    export_params=True,  # 存储训练好的参数
    opset_version=13,  # ONNX 算子集版本（推荐 11~17，13 兼容性好）
    do_constant_folding=True,  # 优化常量
    input_names=['input'],  # 输入名（可自定义）
    output_names=['output'],  # 输出名（可自定义）
    dynamic_axes={
        'input': {0: 'batch_size'},  # 动态 batch 维度
        'output': {0: 'batch_size'}
    }
)

print(f"✅ 模型已成功导出为 ONNX 格式: {onnx_file_path}")
