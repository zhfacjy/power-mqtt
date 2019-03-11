# 本项目用于电表数据采集与保存

# 介绍
EmqSubscribe类订阅emq消息再推送给Kafka，后续Kafka会逐个存入数据库。

# 部署

## 前置条件
- 系统环境：Ubuntu 16.04
- JDK版本：1.8

## Kafka安装
```
# 下载
wget http://apache.cs.utah.edu/kafka/2.1.0/kafka_2.11-2.1.0.tgz
# 解压
tar -xzf kafka_2.11-2.1.0.tgz
# 进入目录
cd kafka_2.11-2.1.0
# 运行
./bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
./bin/kafka-server-start.sh -daemon config/server.properties
```
- 参考资料
  - 快速安装Kafka：https://kafka.apache.org/quickstart
  - 后台运行Kafka应用：https://gist.github.com/piatra/0d6f7ad1435fa7aa790a

## 数据库配置
- 请参考`power-api`的`README.md`

## 应用内配置Kafka
（内网环境下）
- 开发环境配置修改：`resources`文件夹下的`application-dev.yml`文件
- 本地环境配置修改：`resources`文件夹下的`application-pord.yml`文件
- 修改字段`spring.kafka.bootstrap-servers`地址为服务器的Kafka地址和端口

## 步骤
1. 将项目打包成Jar包
2. 上传到服务器
3. 运行`nohup java -jar [文件名].jar &`命令启动应用