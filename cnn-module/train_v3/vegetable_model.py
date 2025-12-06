import torch.nn as nn


class VegetableCNN(nn.Module):
    """
    卷积神经网络模型，用于蔬菜图像分类。
    使用 AdaptiveAvgPool2d 以支持不同尺寸输入。
    """

    def __init__(self, num_classes=5):
        super(VegetableCNN, self).__init__()

        # 定义特征提取网络
        self.features = nn.Sequential(
            # 第一个卷积块
            # 输入: (3, H, W) -> 输出: (16, H-4, W-4) 因为 kernel_size=5, stride=1
            nn.Conv2d(3, 16, 5),  # 3输入通道(RGB), 16输出通道, 卷积核5x5
            nn.ReLU(),
            # 经过 MaxPool2d(2,2): 尺寸减半 -> (16, (H-4)//2, (W-4)//2)
            nn.MaxPool2d(2, 2),  # kernel_size=2, stride=2

            # 第二个卷积块
            # 输入: (16, (H-4)//2, (W-4)//2) -> 输出: (32, (H-4)//2 - 4, (W-4)//2 - 4)
            nn.Conv2d(16, 32, 5),  # 输入16通道，输出32通道
            nn.ReLU(),
            # 经过 MaxPool2d(2,2): 尺寸再减半 -> (32, ((H-4)//2 - 4)//2, ((W-4)//2 - 4)//2)
            nn.MaxPool2d(2, 2),

            # 第三个卷积块
            # 输入: (32, ...) -> 输出: (64, ... - 2) 因为 kernel_size=3
            nn.Conv2d(32, 64, 3),  # 输入32通道，输出64通道，卷积核3x3
            nn.ReLU(),
            # 经过 MaxPool2d(2,2): 尺寸再减半
            nn.MaxPool2d(2, 2),
        )

        # 全局自适应平均池化层
        # 无论输入特征图尺寸如何，都将其压缩为 (64, 1, 1)
        # 这样可以处理任意尺寸的输入图像，解决了固定尺寸的问题
        self.global_pool = nn.AdaptiveAvgPool2d((1, 1))

        # 定义分类器（全连接层）
        self.classifier = nn.Sequential(
            nn.Flatten(),  # 将 (64, 1, 1) 展平为 (64,)
            nn.Linear(64, 512),  # 64 -> 512
            nn.ReLU(),
            nn.Linear(512, 128),  # 512 -> 128
            nn.ReLU(),
            nn.Linear(128, num_classes)  # 128 -> num_classes (如5)
            # CrossEntropyLoss 内部处理，数值更稳定
        )

    def forward(self, x):
        # 前向传播
        x = self.features(x)  # 卷积特征提取
        x = self.global_pool(x)  # 自适应池化，输出固定为 (batch_size, 64, 1, 1)
        x = self.classifier(x)  # 分类
        return x
