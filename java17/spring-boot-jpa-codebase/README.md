# 광고 서비스_매일모으기 

### 구현기능
- 구현범위
  - 광고 등록 API
    - `POST /v1/ads`
      - 광고를 등록하고 `중복 광고명 방지`
      - 광고 ID 를 `유니크하게 생성`
  - 광고 조회 API
    - `GET /v1/ads`
      - `조회 시점에 참여가 가능한 광고만 노출`
      - `적립 액수가 높은 순으로 정렬`
      - `광고 참여 가능 횟수가 소진된 광고는 노출되지 않음`
      - `최대 10 개` 의 광고를 조회
  - 광고 참여 API
    - `POST /v1/adParticipations`
      - 광고 참여를 하고 `광고의 참여 가능 횟수를 차감, 포인트 적립(적립 API)`
      - 광고 참여 요청 처리 시점에 `광고 참여 가능 횟수가 0이면 참여 불가 에러를 응답`
      - 미등록 광고 ID로 요청이 올 경우 `미등록 광고 에러를 응답`
      - `하나의 광고에 여러 유저가 동시에 참여할 수 있음`
  - 광고 참여 이력 조회 API
    - `GET /v1/adParticipations`
      - `광고 참여 시각이 오래된 순으로 조회`
      - `페이지네이션 적용 (한번에 최대 50개 이력을 조회)`
  - 각 기능별 통합 및 유닛 테스트 구현 
  - Redis 를 사용한 분산락 구현
  - MapStruct 를 적용한 Java Bean 유형 간의 매핑 구현 단순화
  - Swagger 를 사용한 문서화
  - API 공통 결과 모델링 및 Exception Handler 적용

### 개발환경
- Java 17
- Spring Boot 3.2.3
- Gradle 8.12.1
- H2 DB
- docker 27.5.1 + docker compose v2.32.4

### IDE Setting
- File -> Settings.. > "Enable annotation processing": Check
- File -> Settings.. > Build, Execution, Deployment > Build Tools > Gradle
  - "Build and run using: IntelliJ IDEA" 로 변경
  - "Run tests using: IntelliJ IDEA" 로 변경
- File -> Settings.. > Plugins > MarketPlace > Lombok 설치

### 접속정보
- [API Document(Swagger)](http://localhost:9090/swagger-ui/index.html)
- [H2-CONSOLE(Local DB)](http://localhost:9090/h2-console)
    - url: jdbc:h2:mem:testdb
    - username: sa

### API 테스트 방법
[Swagger](http://localhost:9090/swagger-ui/index.html) 를 통하여 진행 부탁 드리겠습니다.
1. [광고 조회 API](http://localhost:9090/swagger-ui/index.html#/%EB%A7%A4%EC%9D%BC%EB%AA%A8%EC%9C%BC%EA%B8%B0/getAdList)
2. [광고 등록 API](http://localhost:9090/swagger-ui/index.html#/%EB%A7%A4%EC%9D%BC%EB%AA%A8%EC%9C%BC%EA%B8%B0/getAdList)
3. [광고 참여 이력조회 API](http://localhost:9090/swagger-ui/index.html#/%EB%A7%A4%EC%9D%BC%EB%AA%A8%EC%9C%BC%EA%B8%B0/getAdParticipationList)
4. [광고 참여 API](http://localhost:9090/swagger-ui/index.html#/%EB%A7%A4%EC%9D%BC%EB%AA%A8%EC%9C%BC%EA%B8%B0/createAdParticipation)

### 빌드 및 실행 방법
- 준비
  - `프로젝트 루트 경로에 있는 docker-compose.yml 을 통하여 redis 설치` (컨테이너로 실행하지 않으시면 직접 레디스 서버를 설치하셔도 됩니다.)
    ```
    docker-compose up
    ```
- 실행
  - IDE
      - Spring Boot Application Run => SpringBootJpaCodebaseApplication.java (default profile: local)
  - Not IDE
    ```
    nohup java -jar spring-boot-jpa-codebase-1.0.0.jar
    ```
- 빌드
  ```
  gradlew :clean :build
  ```
- 유닛 테스트
  ```
  gradlew unitTest
  ```
- 통합 테스트
  ```
  gradlew integrationTest
  ```
