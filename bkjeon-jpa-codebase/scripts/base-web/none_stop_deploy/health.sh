#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)/none_stop_deploy
source ${ABSDIR}/profile.sh

function switch_proxy() {
  echo "> Switch Proxy Start"
  IDLE_PORT=$(sudo cat /etc/nginx/conf.d/current-port)

  echo "> 전환할 Port: $IDLE_PORT"
  echo "> Port Change"

  # 앞에서 넘겨준 문장을 service-url.inc에 덮어쓴다
  echo "set \$service_url http://localhost:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

  # 최종적으로 실행한 포트의 값을 저장
  sudo cat > /etc/nginx/conf.d/current-port

  echo "> nginx reload"
  sudo service nginx reload
}