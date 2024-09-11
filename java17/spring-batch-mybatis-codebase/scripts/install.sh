#!/bin/sh

emma_check=`ps -ef | grep -v "grep" | grep "docker" | wc -l`
echo $emma_check
if [ $emma_check -lt 3 ]; then
    echo "Docker is not running"
    exit 1
fi



echo "==========================================="
echo "Install Start"
echo "==========================================="

echo '                          (0%)\r'
sleep 1
project_path=$(pwd)/docker
cd ${project_path}
docker-compose -p spring-batch-mybatis-codebase -f docker-compose.yml up -d

echo '##########                     (33%)\r'
sleep 1

echo '##########################             (66%)\r'
sleep 1

echo '##############################################   (100%)\r'
sleep 1
echo '\n'