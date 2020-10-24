#!/usr/bin/env bash

# 쉬고 있는 profile 찾기
function find_idle_profile() {
  # nginx가 바라보고 있는 springboot를 http statue로 정상동작 여부를 확인한다.
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:9000/profile)

  # 400 이상은 모두 예외로 보고 prod2를 현재 profile로 사용
  if [ ${RESPONSE_CODE} -ge 400 ]
  then
    CURRENT_PROFILE=prod2
  else
    CURRENT_PROFILE=$(curl -s http://localhost:9000/profile)
  fi

  # 스프링부트를 해당 profile로 연결하기 위해 반환
  if [ ${CURRENT_PROFILE} == prod1 ]
  then
    IDLE_PROFILE=prod2
  else
    IDLE_PROFILE=prod1
  fi

  # bash script는 반환기능이 없으므로 echo로 결과를 출력 후 find_idle_port 함수에서 사용
  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == prod1 ]
  then
    echo "9001"
  else
    echo "9002"
  fi
}