# 项目部署方案

## 必要插件
### Nginx
宝塔安装【Nginx】即可

```shell script
# 创建配置目录
mkdir /www/server/nginx/conf/conf.d
# 创建证书目录
mkdir /www/server/nginx/conf/ssl
# 将document下的ssl放入证书目录
# china.conf配置放入conf.d下面

# 重启
nginx -t && nginx -s reload
```

### JDK
宝塔安装【Java项目管理器 2.5】即可
下载Tomcat 8

### 图片资源包
```shell script
# 下载图片资源包，最好是同国家的网络
scp username@ip:/www/prod/resources/chinatruck/parts_images.zip /www/prod/resources/chinatruck/
# 解压
tar -zxvf parts_images.zip
```

### 数据迁移
```
# 请求地址
POST http://ip:9100/queue/transferData

# body
document/transfer_data.json
```

## 物理机
```shell script
git clone <repo>

mvn clean install

nohup java -jar target/chinatruck.jar &
```
需要修改`src/main/resources/application.properties`下Mongo仓库配置

## 容器(docker)
- 安装

宝塔安装【Docker管理器 3.6】

- 修改配置

修改项目下的Dockerfile文件`-Dspring.data.mongodb.uri=mongodb://{username}:{password}@{ip}:{port}`

```shell script
sh rebuild.sh
```