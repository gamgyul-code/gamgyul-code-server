#!/bin/bash

PROJECT_PATH="/home/ubuntu/gamgyul-code-server"
DEPLOY_LOG_PATH="$PROJECT_PATH/logs/deploy.log"
DEPLOY_ERR_LOG_PATH="$PROJECT_PATH/logs/deploy_err.log"
APPLICATION_LOG_PATH="$PROJECT_PATH/logs/application.log"
BUILD_PATH="$PROJECT_PATH/build/libs"
JAR_PATH="$BUILD_PATH/*.jar"
BUILD_JAR=$(ls $JAR_PATH)
JAR_NAME=$(basename $BUILD_JAR)

echo "===== 배포 시작 : $(date +%c) =====" >> $DEPLOY_LOG_PATH
echo "> build 파일명: $JAR_NAME" >> $DEPLOY_LOG_PATH

echo "> 애플리케이션 재구동" >> $DEPLOY_LOG_PATH
CURRENT_PID=$(pgrep -f "$JAR_NAME")

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_LOG_PATH
else
    echo "> 현재 구동 중인 애플리케이션 종료: PID $CURRENT_PID" >> $DEPLOY_LOG_PATH
    sudo kill -9 $CURRENT_PID
    sleep 5
fi

CUR_DTTM=$(date +%Y%m%d%H%M%S)
NOHUP_BACKUP_PATH="$PROJECT_PATH/nohup"
mkdir -p $NOHUP_BACKUP_PATH
if [ -f "$BUILD_PATH/nohup.out" ]; then
    echo "nohup 백업 : nohup-${CUR_DTTM}.out" >> $DEPLOY_LOG_PATH
    mv $BUILD_PATH/nohup.out $NOHUP_BACKUP_PATH/nohup-${CUR_DTTM}.out
fi

DEPLOY_JAR="$BUILD_PATH/$JAR_NAME"
echo "> 새 애플리케이션 배포: $DEPLOY_JAR" >> $DEPLOY_LOG_PATH
sudo nohup java -jar -Dspring.profiles.active=dev $DEPLOY_JAR >> $APPLICATION_LOG_PATH 2>> $DEPLOY_ERR_LOG_PATH & 
sleep 3
echo "> 배포 종료 : $(date +%c)" >> $DEPLOY_LOG_PATH