#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)/none_stop_deploy

# source(=java의 import 구문과 비슷) -> profile.sh의 function 사용
source ${ABSDIR}/profile.sh

IDLE_PORT=$(find_current_port)
echo "> $IDLE_PORT 에서 구동 중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})
echo "> 현재 구동중인 pid: $IDLE_PID"

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi