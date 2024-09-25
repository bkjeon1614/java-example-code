#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)/none_stop_deploy
source ${ABSDIR}/profile.sh

REPOSITORY=/home/bkjeon/app
PROJECT_NAME=base-web

echo "> Build File Copy $REPOSITORY"
echo "> cp $REPOSITORY/${PROJECT_NAME}*.jar $REPOSITORY/"
cp $REPOSITORY/${PROJECT_NAME}*.jar $REPOSITORY/deploy/

echo "> New Application Deploy"
JAR_NAME=$(ls -tr $REPOSITORY/${PROJECT_NAME}*.jar | tail -n 1)

echo "> JAR Name: $JAR_NAME"
echo "> $JAR_NAME Start Role Add"
chmod +x $JAR_NAME

echo "> $JAR_NAME Start"
IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME => profile=$IDLE_PROFILE Start"
nohup java -jar -Dspring.profiles.active=$IDLE_PROFILE $JAR_NAME > $REPOSITORY/deploy/nohup.out 2>&1 &