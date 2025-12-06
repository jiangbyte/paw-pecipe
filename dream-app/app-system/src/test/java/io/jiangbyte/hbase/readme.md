Java 17+ 的模块系统（JPMS）限制了反射访问

添加 JVM 启动参数（推荐，简单有效）
在运行 Java 程序时，添加以下 JVM 参数，开放 java.nio 模块给未命名模块：

--add-opens java.base/java.nio=ALL-UNNAMED

20:45:30.230 [main] WARN org.apache.hadoop.hbase.unsafe.HBasePlatformDependent -- java.nio.Bits#unaligned() check failed.Unsafe based read/write of primitive types won't be used
java.lang.reflect.InaccessibleObjectException: Unable to make static boolean java.nio.Bits.unaligned() accessible: module java.base does not "opens java.nio" to unnamed module @4afcd809
at java.base/java.lang.reflect.AccessibleObject.throwInaccessibleObjectException(AccessibleObject.java:391)
at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:367)
at java.base/java.lang.reflect.AccessibleObject.checkCanSetAccessible(AccessibleObject.java:315)
at java.base/java.lang.reflect.Method.checkCanSetAccessible(Method.java:203)
at java.base/java.lang.reflect.Method.setAccessible(Method.java:197)
at org.apache.hadoop.hbase.unsafe.HBasePlatformDependent.checkUnaligned(HBasePlatformDependent.java:176)
at org.apache.hadoop.hbase.unsafe.HBasePlatformDependent.<clinit>(HBasePlatformDependent.java:49)
at org.apache.hadoop.hbase.util.Bytes.<clinit>(Bytes.java:130)
at org.apache.hadoop.hbase.client.ConnectionUtils.<clinit>(ConnectionUtils.java:192)
at org.apache.hadoop.hbase.client.ConnectionImplementation.<init>(ConnectionImplementation.java:325)
at org.apache.hadoop.hbase.client.ConnectionImplementation.<init>(ConnectionImplementation.java:297)
at java.base/jdk.internal.reflect.DirectConstructorHandleAccessor.newInstance(DirectConstructorHandleAccessor.java:62)
at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:502)
at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:486)
at org.apache.hadoop.hbase.client.ConnectionFactory.lambda$null$0(ConnectionFactory.java:266)
at java.base/java.security.AccessController.doPrivileged(AccessController.java:714)
at java.base/javax.security.auth.Subject.doAs(Subject.java:525)
at org.apache.hadoop.security.UserGroupInformation.doAs(UserGroupInformation.java:1953)
at org.apache.hadoop.hbase.security.User$SecureHadoopUser.runAs(User.java:328)
at org.apache.hadoop.hbase.client.ConnectionFactory.lambda$createConnection$1(ConnectionFactory.java:265)
at org.apache.hadoop.hbase.trace.TraceUtil.trace(TraceUtil.java:216)
at org.apache.hadoop.hbase.client.ConnectionFactory.createConnection(ConnectionFactory.java:251)
at org.apache.hadoop.hbase.client.ConnectionFactory.createConnection(ConnectionFactory.java:133)
at io.jiangbyte.hbase.HbaseConnectionTest.main(HbaseConnectionTest.java:30)
20:45:30.594 [main] INFO org.apache.zookeeper.common.X509Util -- Setting -D jdk.tls.rejectClientInitiatedRenegotiation=true to disable client-initiated TLS renegotiation



https://github.com/cdarlint/winutils



20:50:16.025 [main] WARN org.apache.hadoop.util.Shell -- Did not find winutils.exe: {}
java.io.FileNotFoundException: java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset. -see https://cwiki.apache.org/confluence/display/HADOOP2/WindowsProblems
at org.apache.hadoop.util.Shell.fileNotFoundException(Shell.java:601)
at org.apache.hadoop.util.Shell.getHadoopHomeDir(Shell.java:622)
at org.apache.hadoop.util.Shell.getQualifiedBin(Shell.java:645)
at org.apache.hadoop.util.Shell.<clinit>(Shell.java:742)
at org.apache.hadoop.util.StringUtils.<clinit>(StringUtils.java:80)
at org.apache.hadoop.conf.Configuration.getBoolean(Configuration.java:1733)
at org.apache.hadoop.hbase.HBaseConfiguration.checkDefaultsVersion(HBaseConfiguration.java:71)
at org.apache.hadoop.hbase.HBaseConfiguration.addHbaseResources(HBaseConfiguration.java:117)
at org.apache.hadoop.hbase.HBaseConfiguration.create(HBaseConfiguration.java:131)
at io.jiangbyte.hbase.HbaseConnectionTest.main(HbaseConnectionTest.java:19)
Caused by: java.io.FileNotFoundException: HADOOP_HOME and hadoop.home.dir are unset.
at org.apache.hadoop.util.Shell.checkHadoopHomeInner(Shell.java:521)
at org.apache.hadoop.util.Shell.checkHadoopHome(Shell.java:492)
at org.apache.hadoop.util.Shell.<clinit>(Shell.java:569)
... 6 common frames omitted