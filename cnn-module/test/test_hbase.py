import happybase

# 连接 Thrift Server（默认端口 9090）
connection = happybase.Connection(host='cluster1', port=9090, timeout=5000)

# 打印所有表名
print("Tables:", connection.tables())

# 创建测试表（如果不存在）
table_name = b'test_table'
if table_name not in connection.tables():
    # 定义列族
    connection.create_table(
        table_name,
        {'cf1': dict()}  # 列族 cf1
    )
    print(f"Table {table_name.decode()} created.")

# 获取表对象
table = connection.table(table_name)

# 写入一行数据
row_key = b'row1'
table.put(row_key, {b'cf1:col1': b'value1'})

# 读取数据
row = table.row(row_key)
print("Read row:", row)

# 扫描表
print("Scan result:")
for key, data in table.scan():
    print(key, data)

# 删除表（可选）
connection.disable_table(table_name)
connection.delete_table(table_name)

connection.close()
print("Test completed.")
