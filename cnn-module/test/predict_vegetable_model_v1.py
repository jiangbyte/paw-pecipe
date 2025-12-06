import torch
import torchvision.transforms as transforms
from PIL import Image
from v2.vegetable_model import VegetableCNN
import happybase  # 如果需要从 HBase 读图；否则可删


# === 1. 重建标签映射（必须和训练时完全一致！）===
def get_label_mapping_from_hbase(host="cluster1", port=9090, table_name="vegetable_image_data"):
    conn = happybase.Connection(host=host, port=port)
    table = conn.table(table_name)
    unique_labels = set()
    for _, data in table.scan(columns=[b"meta:label"]):
        label = data[b"meta:label"].decode("utf-8")
        unique_labels.add(label)
    conn.close()
    sorted_labels = sorted(unique_labels)
    label_to_idx = {label: idx for idx, label in enumerate(sorted_labels)}
    idx_to_label = {v: k for k, v in label_to_idx.items()}
    return label_to_idx, idx_to_label


# === 2. 加载模型 ===
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")

# 获取类别数
_, idx_to_label = get_label_mapping_from_hbase()
num_classes = len(idx_to_label)

model = VegetableCNN(num_classes=num_classes)
model.load_state_dict(torch.load("result_model/CNN_Vegetable_Model_best.pt", map_location=device))
model.to(device)
model.eval()  # 切换到评估模式！

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
    image_file = "./test/IMAGES_TRAIN/Broccoli/0001.jpg"
    label, conf = predict_image(image_file)
    print(f"预测结果: {label} (置信度: {conf:.4f})")