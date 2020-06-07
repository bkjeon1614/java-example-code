# Batch Code Base

## Development Environment 
* IDE : IntelliJ IDEA Ultimate
* SpringBoot 2.2.2.RELEASE
* Java11
* Gradle

## Prerequisites
- Install JDK 11 ([download](https://www.oracle.com/java/technologies/java-archive-javase11-downloads.html))

## Setting
- File -> Settings.. -> Editor -> File Encoding -> "Project Encoding": UTF-8, "Default encoding for properties files": UTF-8
- File -> Settings.. -> Build, Execution, Deployment -> Compiler -> Annotation Processors -> "Enable annotation processing" Check
- Git Template Setting
  ```
    $ git config --global commit.template {Project Path}/git-commit-template.txt
    
    // manual
    $ git add .
    $ git commit
  ```
  
 ## Batch Start
 ```
 java -jar -Dspring.profiles.active=local -Dfile.encoding=UTF-8 --job.name={jobName} version={versionNum} -Xms256M -Xmx512M
 ```