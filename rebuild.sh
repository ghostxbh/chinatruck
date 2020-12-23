#!/bin/bash

src=/www/prod/chinatruck
name=chinatruck
port=9100

cd $src

echo "拉取新代码"
git pull

echo "以下为最新推送日志:"
echo "======= 最近更新 ======"
git log | head -n 10
echo "======================"

mvn clean package

echo "停止容器"
docker stop $name

echo "删除容器"
docker rm $name

echo "删除镜像"
docker rmi $name

echo "构建镜像"
docker build -t $name .

echo "启动镜像"
docker run -d -p $port:$port $name

echo "docker启动 $name 成功"

