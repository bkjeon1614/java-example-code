#!/usr/bin/env bash

# 현재 실행중인 profile 찾기
function find_current_profile() {
  # nginx가 바라보고 있는 springboot를 http statue로 정상동작 여부를 확인한다.
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:9001/profile)

  if [ ${RESPONSE_CODE} == 200 ]
  then
    CURRENT_PROFILE=$(curl -s http://localhost:9001/profile)
  else
    CURRENT_PROFILE=prod2
  fi

  # bash script는 반환기능이 없으므로 echo로 결과를 출력 후 find_idle_port 함수에서 사용
  echo "${CURRENT_PROFILE}"
}

# 쉬고 있는 profile 찾기
function find_idle_profile() {
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:9001/profile)

  if [ $RESPONSE_CODE == 200 ]
  then
    CURRENT_PROFILE=prod2
  else
    CURRENT_PROFILE=$(curl -s http://localhost:9001/profile)
  fi

  # 만약 실행중인 애플리케이션이 아무것도 없을 때 prod1 기본할당
  if [ -z "$CURRENT_PROFILE" ]
  then
    CURRENT_PROFILE=prod1
  fi

  echo "${CURRENT_PROFILE}"
}

# 현재 사용중인 profile의 port 찾기
function find_current_port() {
  IDLE_PROFILE=$(find_current_profile)

  if [ ${IDLE_PROFILE} == prod1 ]
  then
    echo "9001"
  else
    echo "9002"
  fi
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == prod1 ]
  then
    echo "9002"
  else
    echo "9001"
  fi
}