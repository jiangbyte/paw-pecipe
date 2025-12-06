import happybase
import time
import random
import io
from PIL import Image


def generate_rowkey(label: str) -> str:
    timestamp = str(int(time.time() * 1000))  # 毫秒时间戳
    suffix = f"{random.randint(0, 65535):04x}"  # 4位随机十六进制
    return f"{label}_{timestamp}_{suffix}"


# 连接
conn = happybase.Connection(host='cluster1', port=9090, timeout=5000)
table = conn.table('vegetable_image_data')

# 插入一张番茄图片
img = Image.open("./tomato.png")
byte_io = io.BytesIO()
img.save(byte_io, format='JPEG', quality=80)
img_bytes = byte_io.getvalue()

rowkey = generate_rowkey("Tomato")
table.put(rowkey, {
    b'img:raw': img_bytes,
    b'meta:label': b'Tomato',
    b'meta:source': b'user_upload',  # or 'crawler', 'partner'
    b'meta:upload_time': str(int(time.time())).encode(),
    b'meta:width': str(img.width).encode(),
    b'meta:height': str(img.height).encode(),
    b'ext:season': b'summer',
    b'ext:origin': b'China'
})
print(f"Inserted row: {rowkey}")
