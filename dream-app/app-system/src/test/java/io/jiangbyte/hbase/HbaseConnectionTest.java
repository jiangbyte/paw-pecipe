//package io.jiangbyte.hbase;
//
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.hbase.HBaseConfiguration;
//import org.apache.hadoop.hbase.TableName;
//import org.apache.hadoop.hbase.client.*;
//import org.apache.hadoop.hbase.util.Bytes;
//
//import java.io.IOException;
//
///**
// * HBase 连接测试 + 扫表分页
// * Hadoop 3.3.6 + HBase 2.6.4 真分布集群
// */
//public class HbaseConnectionTest {
//
//    public static void main(String[] args) {
//        // 1. 创建配置
//        Configuration config = HBaseConfiguration.create();
//        config.set("hbase.zookeeper.quorum", "192.168.142.100");
//        config.set("hbase.zookeeper.property.clientPort", "2181");
//        // 设置超时（避免长时间卡住）
//        config.setLong("hbase.rpc.timeout", 5000);
//        config.setLong("hbase.client.operation.timeout", 5000);
//
//        Connection connection = null;
//        try {
//            // 2. 建立连接
//            System.out.println("正在连接 HBase 集群...");
//            connection = ConnectionFactory.createConnection(config);
//            System.out.println("✅ 连接成功！");
//
//            // 3. 测试扫表 vegetable_image_data
//            scanTableWithPagination(connection, "vegetable_image_data", 5);
//
//        } catch (Exception e) {
//            System.err.println("❌ 操作失败: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            // 4. 关闭连接
//            if (connection != null) {
//                try {
//                    connection.close();
//                    System.out.println("已关闭连接");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    /**
//     * 简单分页扫描表（每次读取 limit 条记录）
//     */
//    public static void scanTableWithPagination(Connection connection, String tableName, int limit) throws IOException {
//        Table table = connection.getTable(TableName.valueOf(tableName));
//        Scan scan = new Scan();
//        scan.setLimit(limit); // HBase 2.0+ 支持 setLimit 直接分页
//
//        System.out.println("\n🔍 正在扫描表 [" + tableName + "]，最多显示 " + limit + " 条记录...\n");
//
//        try (ResultScanner scanner = table.getScanner(scan)) {
//            int count = 0;
//            for (Result result : scanner) {
//                count++;
//                String rowKey = Bytes.toString(result.getRow());
//                System.out.println("RowKey: " + rowKey);
//
//                // 打印 meta 列族（避免打印二进制图片）
//                result.getColumnCells(Bytes.toBytes("meta"), Bytes.toBytes("label"))
//                        .forEach(cell ->
//                                System.out.println("  meta:label = " + Bytes.toString(cell.getValueArray(),
//                                        cell.getValueOffset(), cell.getValueLength()))
//                        );
//
//                result.getColumnCells(Bytes.toBytes("meta"), Bytes.toBytes("source"))
//                        .forEach(cell ->
//                                System.out.println("  meta:source = " + Bytes.toString(cell.getValueArray(),
//                                        cell.getValueOffset(), cell.getValueLength()))
//                        );
//
//                if (count >= limit) break;
//            }
//
//            if (count == 0) {
//                System.out.println("⚠️  表为空或无匹配数据");
//            } else {
//                System.out.println("✅ 共读取 " + count + " 条记录");
//            }
//        }
//    }
//}