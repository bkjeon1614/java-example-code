# API Code Base (삭제예정 멀티 프로젝트로 전환)

## Domain
- Application: http://localhost:8080
- H2 DB: http://localhost:8080/h2-console
- Swagger: http://localhost:8080/swagger-ui.html

## Development Environment 
* IDE : IntelliJ IDEA Ultimate
* SpringBoot 2.2.2.RELEASE
* Java11
* Gradle

## Prerequisites
- Install JDK 10 ([download](https://www.oracle.com/java/technologies/java-archive-javase11-downloads.html))

## Setting
- File -> Settings.. -> Editor -> File Encoding -> "Project Encoding": UTF-8, "Default encoding for properties files": UTF-8
- File -> Settings.. -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> "Enable annotation processing" Check

## Build
```
gradle fullBuild
```

## App Start
```
java -jar -Dspring.profiles.active=local -Dfile.encoding=UTF-8 -Xms256M -Xmx512M
```