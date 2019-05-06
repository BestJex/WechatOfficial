#!/bin/bash

#设置全局变量
PACKAGE_NAME=wxpublic.war

BASH_DIR=$(cd $(dirname $0); pwd)
PROJECT_DIR=$(dirname $BASH_DIR)

echo_error(){
    echo `date +"%Y-%m-%d %H:%M:%S"` "[ERROR]" $1
}

echo_info(){
    echo `date +"%Y-%m-%d %H:%M:%S"` "[INFO]" $1
}

#退出函数
check_or_exist(){
    if [ $? -ne 0 ]; then
        echo_error $1
        cp $BASH_DIR/pom_dev.xml $PROJECT_DIR/pom.xml
        exit $2
    fi
}
