#!/bin/bash

# Code Version Down Deploy
# init
# chmod +x ./start_ci_application.sh
REPOSITORY=/home/bkjeon/app/java-example-code
PROJECT_NAME=bkjeon-jpa-codebase
MODULE_NAME=base-web

echo "> Build File Copy"
cp $REPOSITORY/$PROJECT_NAME/$MODULE_NAME/build/libs/*.jar $REPOSITORY/

echo "> Application Started PID Check"
CURRENT_PID=$(pgrep -f ${MODULE_NAME}*.jar)
echo "> Application Started PID Number: $CURRENT_PID"

echo "> Application Kill"
if [ -z "$CURRENT_PID" ]; then
  echo "> Not Started Application"
else
  echo "> Kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> New Application Deploy"
JAR_NAME=$(ls -tr $REPOSITORY/ | grep *.jar | tail -n 1)
echo "> JAR Name: $JAR_NAME"
nohup java -jar -Dspring.profiles.active=dev $REPOSITORY/$JAR_NAME &
