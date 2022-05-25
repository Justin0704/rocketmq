# rocketmq
rocketMQ - 学习

roketMQ的安装与使用

一、官方下载地址：

https://rocketmq.apache.org/dowloading/releases/

先择版本4.7.1, 解压， 配置环境变量：

ROCKETMQ_HOME

D:\java\rocketmq-all-4.7.1-bin-release

二、启动：

1、启动nameserver：start mqnamesrv.cmd

2、启动broker：start mqbroker.cmd -n 127.0.0.1:9876 autoCreateTopicEnable=true

若是启动mqbroker报错误，例如：找不到主类，则打开runbroker.cmd,将classpath加个英文双引号：
"%CLASSPATH%""

三、使用可视化界面：

下载：https://codeload.github.com/apache/rocketmq-externals/zip/master

进入：rocketmq-externals-master/rocketmq-console/src/main/resources/application.properties修改配置

控制台端口：
server.port=8089

修改RocketMq的服务地址：
rocketmq.config.namesrvAddr=localhost:9876

进入rocketmq-externals/rocketmq-console文件夹执行：mvn clean package -Dmaven.test.skip=true, 进行编译

编译成功后，再它的target下生成rocketmq-console-ng-2.0.0.jar
执行：java -jar rocketmq-console-ng-2.0.0.jar, 启动此jar包

启动后，再浏览器中输入 http://localhost:8089








