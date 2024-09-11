JPA Code Base
=========

## 1. Project Structure
bkjeon-jpa-codebase <br>
&nbsp;&nbsp; |- [base-common](/base-common) : 공통 클래스 모듈 <br>
&nbsp;&nbsp; |- [base-api](/base-api) : API <br>
&nbsp;&nbsp; |- [base-batch](/base-batch) : Batch <br>
&nbsp;&nbsp; |- [base-web](/base-web) : Web (mustache) <br>

## 2. Development Environment 
* IDE : IntelliJ IDEA Ultimate
* SpringBoot 2.2.2.RELEASE
* SpringBatch 2.2.2 RELEASE
* Java11
* Gradle
* Mustache

## 3. Prerequisites
- Install JDK 11 ([download](https://www.oracle.com/java/technologies/java-archive-javase11-downloads.html))

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
- QueryDSL Compile
  - auth-common/src/main/generated/** 에 생성됨
  - 우측 gradle view -> auth-common -> Task -> other -> compileJava
  - 코드상에서 각 클래스 generated의 Q로 시작되는 class를 static import 필요

## 5. jasypt key create
- path: /{projectName}/file/jasypt-1.9.3
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
