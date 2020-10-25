#!/usr/bin/env bash

SCRIPT_PATH=/home/bkjeon/app/java-example-code/bkjeon-jpa-codebase/scripts/base-web

# source(=java의 import 구문과 비슷) -> profile.sh의 function 사용
source ${SCRIPT_PATH}/profile.sh

IDLE_PORT=$(find_idle_port)
echo "> $IDLE_PORT 에서 구동 중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi