FROM openjdk:8-alpine

RUN echo "Asia/Shanghai" > /etc/timezone

# Required for starting application up.
RUN apk update && apk add /bin/sh

RUN mkdir -p /opt/app
ENV PROJECT_HOME /opt/app

COPY target/chinatruck.jar $PROJECT_HOME/chinatruck.jar

WORKDIR $PROJECT_HOME

CMD ["java", "-Dspring.data.mongodb.uri=mongodb://root:zxcASDqwe@192.30.242.218:27017","-jar","./chinatruck.jar"]