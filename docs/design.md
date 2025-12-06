
docker run -d \
--name mq-rabbitmq \
-p 15672:15672 \
-p 5672:5672 \
-e RABBITMQ_DEFAULT_USER=root \
-e RABBITMQ_DEFAULT_PASS=123456 \
-v rabbitmq_data:/var/lib/rabbitmq \
my-rabbitmq-delay


---

## 🌿 项目名称建议

**VeggieChef：智能蔬菜识别与个性化食谱助手**

> —— “拍一张菜，教你怎么做。”

---

## 🎯 核心价值主张

- **对用户**：厨房小白也能轻松处理不熟悉的蔬菜；减少食物浪费；吃得更健康。
- **对技术**：完美融合 **自训练CNN模型（识别） + 多模态大模型（理解+生成） + 大数据存储（HBase记录偏好）**。

---

## 🔁 工作流程（两阶段清晰分离）

```mermaid
sequenceDiagram
    participant User as 用户（App/Web）
    participant API as 后端（Java）
    participant MinIO
    participant MQ as RabbitMQ
    participant Worker as AI Worker（Python）
    participant CNN as 自训练模型（识别蔬菜）
    participant LLM as 多模态大模型（生成食谱）
    participant HBase

    User->>API: 上传一张蔬菜照片（如带泥的芋头）
    API->>MinIO: 存图，生成image_id
    API->>MQ: 发送任务 {image_id, user_id}
    Worker->>MinIO: 下载图片
    Worker->>CNN: 调用模型 → 输出 "Taro" (假设label=64)
    Worker->>LLM: 构造Prompt：
        “这是一张被识别为‘芋头’的蔬菜照片。
         请结合其外观（新鲜/带泥/切开等），
         推荐2道适合家庭制作的中式家常菜，
         包含简要做法和所需主要调料。
         回答不超过120字。”
    LLM-->>Worker: “推荐：1. 芋头蒸排骨：芋头切块垫底，放腌好的排骨蒸30分钟。2. 香煎芋丝饼：芋头擦丝，加面粉、盐、葱花煎至金黄。”
    Worker->>HBase: 存储 {veggie: 芋头, recipes: "...", image_id, user_id}
    API->>User: 返回识别结果 + 食谱建议
```

---

## 🥦 数据与模型调整（最小改动，最大效果）

### 1. **扩展你的 label_dict（仅保留/强化蔬菜类）**

你当前的 `label_dict` 中已有部分蔬菜/水果，可做如下调整：

```python
# 新的 veggie_label_dict（示例，可扩展至50+常见蔬菜）
veggie_label_dict = {
    'Apple': 0, 'Banana': 1, 'Orange': 2,
    'Tomato': 3,          # 蔬菜！
    'Carambola': 4,       # 杨桃
    'Kiwi': 5, 'Peach': 6, 'Pear': 7,
    'Persimmon': 8,       # 柿子
    'Pitahaya': 9,        # 火龙果
    'Plum': 10, 'Pomegranate': 11,
    'Mulberry': 12, 'Mongo': 13,  # 芒果
    'Hami melon': 14,     # 哈密瓜
    # === 新增或强调的蔬菜 ===
    'Potato': 15,
    'Carrot': 16,
    'Cabbage': 17,
    'Eggplant': 18,       # 茄子
    'Cucumber': 19,
    'Spinach': 20,
    'Broccoli': 21,
    'Cauliflower': 22,
    'Onion': 23,
    'Garlic': 24,
    'Ginger': 25,
    'Taro': 26,           # 芋头
    'Lotus_root': 27,     # 莲藕
    'Bitter_gourd': 28,   # 苦瓜
    'Zucchini': 29,       # 西葫芦
    # ... 可继续扩充
}
```

> ✅ **操作建议**：重新整理训练数据，只保留 **蔬菜、水果、常见香料**（如姜、蒜），移除动物类（如elephant、zebra）。你的 `fruitdataset.py` 可重命名为 `veggiedataset.py`。

---

### 2. **训练模型（04.训练模型.py 微调）**

- 输入：100x100 RGB 图像
- 输出：N 类蔬菜/水果（如 N=50）
- 模型结构不变（你的 `FruitModel` 完全适用）
- 最终保存为 `VeggieClassifier_best.pt`

---

### 3. **多模态大模型 Prompt 设计（关键！）**

```python
def build_recipe_prompt(veggie_name: str, freshness_hint: str = "") -> str:
    base = f"你是一位精通中华家常菜的厨师。这张图片被识别为'{veggie_name}'。"
    if freshness_hint:
        base += f"根据外观判断：{freshness_hint}。"
    return base + """
请推荐2道简单易做的家常菜，要求：
- 适合1-2人份
- 主要调料不超过5种（如油、盐、酱油等常见品）
- 每道菜用一句话说明核心做法
- 总字数不超过120字
用中文回答。
"""
```

> 💡 **进阶**：可结合用户画像（来自MySQL）加入偏好，如“用户有糖尿病，请推荐低糖做法”。

---

## 🗃️ 大数据技术使用（HBase + MySQL）

| 存储系统      | 用途                                                                                                            |
|-----------|---------------------------------------------------------------------------------------------------------------|
| **HBase** | 存储原始识别记录：<br>`rowkey=image_id`<br>`veggie:name`, `veggie:confidence`<br>`recipe:text`, `recipe:model_version` |
| **MySQL** | 存储用户信息：<br>`users(id, name, dietary_preference, allergies)`<br>`user_history(user_id, veggie, liked_recipe)`  |
| **Redis** | 缓存热门蔬菜的通用食谱（如“西红柿”的10种做法），减少LLM调用                                                                             |

---

## 🖼️ 用户界面升级（基于 06.界面识别水果.py）

- 标题改为：**“🥦 智能蔬菜识别 & 食谱推荐”**
- 识别结果显示：
  ```text
  识别结果：茄子
  推荐菜谱：
  1. 鱼香茄子：茄子切条煎软，加豆瓣酱、蒜末、糖醋汁翻炒。
  2. 蒜蓉蒸茄子：茄子蒸熟撕条，淋热油、生抽、蒜蓉。
  ```
- 增加按钮：“👍 喜欢这个菜谱” → 记录到MySQL，用于个性化推荐

---

## 🌐 应用场景举例

| 用户上传       | CNN 识别   | 多模态大模型输出                                                    |
|------------|----------|-------------------------------------------------------------|
| 一根带泥胡萝卜    | Carrot   | “推荐：1. 胡萝卜炒肉片：胡萝卜切片，与猪肉快炒，加酱油调味。2. 胡萝卜玉米排骨汤：焯水后与排骨、玉米炖1小时。” |
| 半颗发蔫的西兰花   | Broccoli | “西兰花略蔫但可食用。推荐清炒：焯水30秒，蒜末爆香后快炒，加盐出锅。”                        |
| 不认识的紫色长条蔬菜 | Eggplant | “这是茄子。推荐地三鲜：与土豆、青椒切块，过油后加酱油、糖焖煮。”                           |

---

## 📦 技术栈完全复用

| 组件               | 用途                                   |
|------------------|--------------------------------------|
| **HBase**        | 存储海量蔬菜图片的识别与食谱结果（`veggie_records` 表） |
| **MinIO**        | 存原始用户上传图                             |
| **RabbitMQ**     | 异步解耦上传与AI处理                          |
| **PyTorch**      | 训练和推理蔬菜分类CNN                         |
| **多模态LLM**       | 生成个性化、可操作的食谱                         |
| **Java + MySQL** | 用户管理、偏好记录、历史查询                       |

---

## ✅ 下一步行动建议

1. **数据准备**：
    - 清理 `./fruit` 目录，只保留蔬菜/水果子文件夹
    - 补充常见蔬菜图片（可爬取或用公开数据集如 Food-101, Veg200）
2. **修改 label_dict 和 dataset**：
    - 将 `fruitdataset.py` 改为 `veggiedataset.py`
    - 更新 `label_transform` 对应新字典
3. **重训练模型**（只需改几行代码）
4. **在 05.识别真实图片.py 和 06.界面.py 中更新标签映射**

---

如果你确认这个方向，我可以立即为你：

- 提供一份 **50种常见蔬菜的英文-中文对照 label_dict**
- 修改 `04.训练模型.py` 的类别数和保存路径
- 重写 `06.界面识别水果.py` 为 **蔬菜食谱助手界面**

请告诉我是否开始？