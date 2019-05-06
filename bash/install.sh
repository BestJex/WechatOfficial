#!/bin/bash

#导入全局变量
. $(dirname $0)/common.sh

#跳转到项目目录
cd $PROJECT_DIR

#复制配制文件
cp $BASH_DIR/pom_prod.xml $PROJECT_DIR/pom.xml

sed -i "s/active: dev/active: prod/g" $PROJECT_DIR/src/main/resources/application.yml
sed -i "s/active: test/active: prod/g" $PROJECT_DIR/src/main/resources/application.yml

echo "===================clean begin==================="
mvn clean
echo "===================clean end  ==================="

check_or_exist "mvn clean error" 1

echo "==================package begin==================="
mvn package -Dmaven.test.skip=true
echo "==================package end  ==================="

check_or_exist "mvn package error" 2

echo "====================scp begin====================="
scp target/$PACKAGE_NAME root@192.168.0.1:/mnt/tomcat/webapps/$PACKAGE_NAME.bak
cmd="mv /mnt/tomcat/webapps/${PACKAGE_NAME}.bak /mnt/tomcat/webapps/${PACKAGE_NAME}"
ssh root@192.168.0.1 $cmd
#scp target/$PACKAGE_NAME root@192.168.0.1:/mnt/tomcat/webapps
echo "====================scp end  ====================="

echo "远程部署成功"

cp $BASH_DIR/pom_dev.xml $PROJECT_DIR/pom.xml

sed -i "s/active: prod/active: dev/g" $PROJECT_DIR/src/main/resources/application.yml
