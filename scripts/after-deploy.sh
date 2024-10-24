PROJECT_PATH="/home/ubuntu/gamgyul-code-server"
LOG_DIR="$PROJECT_PATH/logs"

# 로그 디렉토리가 존재하지 않으면 생성
mkdir -p "$LOG_DIR"

DEPLOY_LOG_PATH="$LOG_DIR/deploy.log"
DEPLOY_ERR_LOG_PATH="$LOG_DIR/deploy_err.log"
APPLICATION_LOG_PATH="$LOG_DIR/application.log"
BUILD_PATH="$PROJECT_PATH"
JAR_PATH="$BUILD_PATH/halmang-vision-0.0.1-SNAPSHOT.jar"
BUILD_JAR=$(ls "$JAR_PATH")
JAR_NAME=$(basename "$BUILD_JAR")

echo "===== 배포 시작 : $(date +%c) =====" >> "$DEPLOY_LOG_PATH"
echo "PROJECT_PATH: $PROJECT_PATH >> "$DEPLOY_LOG_PATH"
echo "BUILD_PATH: $BUILD_PATH >> "$DEPLOY_LOG_PATH"
echo "JAR_PATH: $JAR_PATH >> "$DEPLOY_LOG_PATH"
echo "BUILD_JAR: $BUILD_JAR >> "$DEPLOY_LOG_PATH"
echo "> build 파일명: $JAR_NAME" >> "$DEPLOY_LOG_PATH"
echo "> build 파일 복사" >> "$DEPLOY_LOG_PATH"
cp "$BUILD_JAR" "$BUILD_PATH"

echo "> 애플리케이션 재구동" >> "$DEPLOY_LOG_PATH"
CURRENT_PID=$(pgrep -f "$JAR_NAME")
echo "현재 구동중인 애플리케이션 PID: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
    echo "> sudo kill -9 $CURRENT_PID"
    sudo kill -9 "$CURRENT_PID"
    sleep 5
fi

CUR_DTTM=$(date +%Y%m%d%H%M%S)
echo "nohup 백업 : nohup-${CUR_DTTM}.out"
mv "$BUILD_PATH/nohup.out" "$PROJECT_PATH/nohup/nohup-${CUR_DTTM}.out"
DEPLOY_JAR="$BUILD_PATH/$JAR_NAME"
echo "> 새 애플리케이션 배포" >> "$DEPLOY_LOG_PATH"
sudo nohup java -jar -Dspring.profiles.active=dev "$DEPLOY_JAR" >> "$APPLICATION_LOG_PATH" 2>> "$DEPLOY_ERR_LOG_PATH" & 
sleep 3
echo "> 배포 종료 : $(date +%c)" >> "$DEPLOY_LOG_PATH"
