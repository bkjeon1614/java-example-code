Gradle Multi Project
=========

- v1: https://bkjeon1614.tistory.com/38
  - 초기세팅
- v2: ?
  - gradle, java, springboot 버전 업그레이드
  - 생성자 인젝션으로 변경
  - 일부 오작동 코드들 수정
  - gradle 버전 업그레이드 대응 (Deprecate 대응)


## 1. Development Environment 
* IDE : IntelliJ IDEA Ultimate
* SpringBoot 2.6.7
* Java11
* Gradle 7.1.1

## 2. Project Structure
sample-multi-module <br>
&nbsp;&nbsp; |- [admin-common](/admin-common) : 공통 클래스 모듈 <br>
&nbsp;&nbsp; |- [admin-api](/admin-api) : WEB 프로젝트 <br>
&nbsp;&nbsp; |- [admin-web](/admin-web) : API 프로젝트 <br>

## 4. Description
해당 프로젝트는(=Gradle Multi Project) IntelliJ IDEA에서만 지원가능합니다.
