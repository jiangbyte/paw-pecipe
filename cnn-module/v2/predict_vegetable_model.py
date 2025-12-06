import torch
import torchvision.transforms as transforms
from PIL import Image
from vegetable_model import VegetableCNN
import json
import os


# === 1. 从保存的文件加载标签映射 ===
def load_label_mapping_from_saved_files(mapping_dir="result_model"):
    """
    从保存的JSON文件加载标签映射
    """
    label_to_idx_path = os.path.join(mapping_dir, "label_to_idx.json")
    idx_to_label_path = os.path.join(mapping_dir, "idx_to_label.json")

    if not os.path.exists(label_to_idx_path) or not os.path.exists(idx_to_label_path):
        raise FileNotFoundError("标签映射文件不存在，请确保训练时已保存标签映射文件")

    with open(label_to_idx_path, "r", encoding="utf-8") as f:
        label_to_idx = json.load(f)

    with open(idx_to_label_path, "r", encoding="utf-8") as f:
        idx_to_label = json.load(f)

    # 将字符串键转换为整数键（JSON默认将所有键作为字符串）
    idx_to_label = {int(k): v for k, v in idx_to_label.items()}

    return label_to_idx, idx_to_label


# === 2. 加载模型 ===
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

# 从保存的文件加载类别映射
label_to_idx, idx_to_label = load_label_mapping_from_saved_files()
num_classes = len(idx_to_label)

model = VegetableCNN(num_classes=num_classes)
model.load_state_dict(torch.load("result_model/CNN_Vegetable_Model_best.pt", weights_only=True, map_location=device))
model.to(device)
model.eval()  # 切换到评估模式！

print("检测到的类别映射:")
for idx, label in idx_to_label.items():
    print(f"  {idx} -> {label}")
print(f"共 {num_classes} 个类别\n")

# === 3. 图像预处理（必须和 test_transform 一模一样！）===
transform = transforms.Compose([
    transforms.Resize((224, 224)),
    transforms.ToTensor(),
    transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
])


# === 4. 预测函数 ===
def predict_image(image_path: str):
    """
    对本地图片进行预测
    """
    img = Image.open(image_path).convert("RGB")
    img_tensor = transform(img).unsqueeze(0).to(device)  # 增加 batch 维度

    with torch.no_grad():
        outputs = model(img_tensor)
        probabilities = torch.softmax(outputs, dim=1)  # 转为概率
        confidence, predicted_idx = torch.max(probabilities, 1)

    predicted_label = idx_to_label[predicted_idx.item()]
    confidence_score = confidence.item()

    return predicted_label, confidence_score


if __name__ == "__main__":
    # 测试一张本地图片
    image_file = "../test/IMAGES_TEST/Bitter_Gourd/1352.jpg"

    label, conf = predict_image(image_file)
    print(f"预测结果: {label} (置信度: {conf:.4f})")

    # 额外测试：显示模型加载信息
    print(f"\n模型已加载成功")
    print(f"模型类别数: {num_classes}")
    print(f"设备: {device}")
