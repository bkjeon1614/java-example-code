Mybatis Code Base
=========

## 1. Project Structure
bkjeon-mybatis-codebase <br>
&nbsp;&nbsp; |- [base-common](/base-common) : 공통 클래스 모듈 <br>
&nbsp;&nbsp; |- [base-api](/base-api) : API <br>
&nbsp;&nbsp; |- [base-batch](/base-batch) : Batch <br>
&nbsp;&nbsp; |- [base-web](/base-web) : WEB (frontend + backend) <br>

## 2. Development Environment 
* IDE : IntelliJ IDEA Ultimate
* SpringBoot 2.2.2.RELEASE
* Java11
* Gradle
* Node 14 And Yarn 1.19

## 3. Prerequisites
- Install JDK 11 ([download](https://jdk.java.net/java-se-ri/11))
- Docker & Etc Tools Install
  ```
  // Docker Install (CentOS8 기준)
  $ sudo yum install -y yum-utils device-mapper-persistent-data lvm2
  $ sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
  $ sudo yum install docker-ce
  
  // MySQL 설치
  $ mkdir -p /home/bkjeon/docker/mysql/data
  $ mkdir -p /home/bkjeon/docker/mysql/config
  
  $ docker run -d -p 13306:3306 -e MYSQL_ROOT_PASSWORD=wjsqhdrms --name bkjeon-mysql -v /home/bkjeon/docker/mysql/data:/var/lib/mysql -v /home/bkjeon/docker/mysql/config:/etc/mysql/conf.d mysql:8.0.17 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --lower_case_table_names=1
  $ docker exec -it bkjeon-mysql bash  
  sql) CREATE DATABASE bkjeon
  sql) CREATE USER 'bkjeon'@'%' IDENTIFIED BY 'wjsqhdrms';
  sql) GRANT ALL PRIVILEGES ON *.* TO 'bkjeon'@'%';
  sql) flush privileges;
  sql) quit  
  
  // Oracle 설치
  $ docker pull jaspeen/oracle-xe-11g
  $ docker run --name oracle11g -d -p 1521:1521 -p 8080:8080 jaspeen/oracle-xe-11g
  $ docker exec -it oracle11g sqlplus
  // system / oracle 로 접속
  SQL> CREATE USER bkjeon IDENTIFIED BY wjsqhdrms;
  SQL> GRANT CREATE SESSION TO bkjeon;
  
  // Elasticsearch 설치
  $ docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name elasticsearch7 docker.elastic.co/elasticsearch/elasticsearch:7.1.1
  $ docker exec -i -t elasticsearch7 cat /usr/share/elasticsearch/config/elasticsearch.yml
  ```

## 4. IDE Setting
- File -> Settings.. -> Editor -> File Encoding -> "Project Encoding": UTF-8, "Default encoding for properties files": UTF-8
- File -> Settings.. -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> "Enable annotation processing" Check
- Git Template Setting
  ```
    $ git config --global commit.template {Project Path}/git-commit-template.txt
    
    // manual
    $ git add .
    $ git commit
  ```

## 5. jasypt key create
- jasypt decode: https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
  - config/security/JasyptEncryptConfig.java
- path: /oy-auth/file/jasypt-1.9.3
  ```
  // H2 DB Config Example
  $ encrypt input="1234" password="bkjeon!@" algorithm="PBEWITHMD5ANDDES"
  ```
- OUTPUT
  [application.yml] 
  ```
  ...
    password: ENC(wJW+IP8qtJmgn1JhOGgptw==)
  ...
  ```

## 6. Description
- 해당 프로젝트는(=Code Base) IntelliJ IDEA에서만 지원가능합니다.
