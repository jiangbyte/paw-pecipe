import onnxruntime as ort
import numpy as np
from PIL import Image
import torchvision.transforms as transforms
import json
import os


class ONNXVegetableClassifier:
    def __init__(self, model_path="result_model/vegetable_classifier.onnx", mapping_dir="result_model"):
        """
        初始化ONNX蔬菜分类器

        Args:
            model_path: ONNX模型文件路径
            mapping_dir: 标签映射文件所在目录
        """
        # 加载ONNX运行时会话
        self.session = ort.InferenceSession(model_path)

        # 加载标签映射
        self.label_to_idx, self.idx_to_label = self._load_label_mapping(mapping_dir)

        # 定义图像预处理变换（固定尺寸）
        self.transform = transforms.Compose([
            transforms.Resize((224, 224)),  # 模型训练时使用224x224
            transforms.ToTensor(),
            transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
        ])

        print("ONNX模型加载成功!")
        print(f"支持的类别数: {len(self.idx_to_label)}")
        print("支持的类别:")
        for idx, label in self.idx_to_label.items():
            print(f"  {idx} -> {label}")

        # 显示模型输入输出信息
        print(f"\n模型输入信息:")
        for input_meta in self.session.get_inputs():
            print(f"  名称: {input_meta.name}, 形状: {input_meta.shape}")
        print(f"模型输出信息:")
        for output_meta in self.session.get_outputs():
            print(f"  名称: {output_meta.name}, 形状: {output_meta.shape}")

    def _load_label_mapping(self, mapping_dir):
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

    def preprocess_image(self, image_path):
        """
        预处理图像：调整大小、转换为张量、标准化

        Args:
            image_path: 图像文件路径

        Returns:
            numpy.ndarray: 预处理后的图像张量 (1, 3, 224, 224)
        """
        # 打开并转换图像
        image = Image.open(image_path).convert("RGB")

        # 应用预处理变换（将任意尺寸图像调整为模型需要的224x224）
        tensor = self.transform(image)

        # 添加批次维度 (1, 3, 224, 224)
        tensor = tensor.unsqueeze(0)

        # 转换为numpy数组 (ONNX运行时需要)
        return tensor.numpy()

    def predict(self, image_path):
        """
        对图像进行预测

        Args:
            image_path: 图像文件路径

        Returns:
            tuple: (预测标签, 置信度分数)
        """
        if not os.path.exists(image_path):
            raise FileNotFoundError(f"图像文件不存在: {image_path}")

        # 预处理图像（自动调整到224x224）
        input_tensor = self.preprocess_image(image_path)

        # 获取输入名称（ONNX模型的输入节点名称）
        input_name = self.session.get_inputs()[0].name

        # 进行预测
        outputs = self.session.run(None, {input_name: input_tensor})

        # 获取预测结果（假设输出是概率分布）
        probabilities = outputs[0][0]  # 取第一个样本的输出

        # 获取最高概率的索引和置信度
        predicted_idx = int(np.argmax(probabilities))
        confidence_score = float(probabilities[predicted_idx])

        # 获取对应的标签
        predicted_label = self.idx_to_label[predicted_idx]

        return predicted_label, confidence_score

    def predict_with_top_k(self, image_path, k=3):
        """
        返回前k个预测结果

        Args:
            image_path: 图像文件路径
            k: 返回前k个预测结果

        Returns:
            list: [(标签, 概率), ...] 按概率降序排列
        """
        if not os.path.exists(image_path):
            raise FileNotFoundError(f"图像文件不存在: {image_path}")

        # 预处理图像
        input_tensor = self.preprocess_image(image_path)

        # 获取输入名称
        input_name = self.session.get_inputs()[0].name

        # 进行预测
        outputs = self.session.run(None, {input_name: input_tensor})

        # 获取预测结果
        probabilities = outputs[0][0]

        # 获取前k个最高概率的索引
        top_k_indices = np.argsort(probabilities)[::-1][:k]

        # 构建结果列表
        results = []
        for idx in top_k_indices:
            label = self.idx_to_label[int(idx)]
            prob = float(probabilities[idx])
            results.append((label, prob))

        return results


def batch_predict(classifier, image_paths):
    """
    批量预测多张图像

    Args:
        classifier: ONNXVegetableClassifier实例
        image_paths: 图像路径列表

    Returns:
        list: [(图像路径, 预测标签, 置信度), ...]
    """
    results = []
    for image_path in image_paths:
        try:
            label, conf = classifier.predict(image_path)
            results.append((image_path, label, conf))
            print(f"图像: {image_path} -> 预测: {label} (置信度: {conf:.4f})")
        except Exception as e:
            print(f"处理图像 {image_path} 时出错: {str(e)}")
            results.append((image_path, "ERROR", 0.0))

    return results


def main():
    # 创建分类器实例
    classifier = ONNXVegetableClassifier()

    # 测试图像路径
    test_image_path = "../test/IMAGES_TEST/Bitter_Gourd/1352.jpg"

    try:
        # 单个预测
        predicted_label, confidence = classifier.predict(test_image_path)
        print(f"\n单个预测结果:")
        print(f"图像: {test_image_path}")
        print(f"预测类别: {predicted_label}")
        print(f"置信度: {confidence:.4f}")

        # Top-3 预测
        top_3_results = classifier.predict_with_top_k(test_image_path, k=3)
        print(f"\nTop-3 预测结果:")
        for i, (label, prob) in enumerate(top_3_results, 1):
            print(f"{i}. {label} (概率: {prob:.4f})")

        # 测试原始图像
        original_size = Image.open(test_image_path).size
        print(f"原始图像尺寸: {original_size}")
        label, conf = classifier.predict(test_image_path)
        print(f"预测结果: {label} (置信度: {conf:.4f})")

    except Exception as e:
        print(f"预测过程中出现错误: {str(e)}")


if __name__ == "__main__":
    main()
