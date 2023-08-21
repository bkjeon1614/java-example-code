# API Code Base

## Info
- Application: http://localhost:9090/api
- Swagger: http://localhost:9090/api/swagger-ui.html
- Zipkin Dashboard: http://localhost:9411
- Spotbugs: http://localhost:9090/api/spotbugs.html
- jacoco: http://localhost:9090/api/jacoco/index.html

## Required
- Redis (Docker Compose)    
  [docker-compose.yml]
  ```
  version: '2'
  
  networks:
  app-tier:
  driver: bridge
  
  services:
  redis:
  image: 'bitnami/redis:latest'
  environment:
  - REDIS_REPLICATION_MODE=master
    - ALLOW_EMPTY_PASSWORD=yes
    networks:
    - app-tier
    ports:
    - 6379:6379
    redis-slave-1:
    image: 'bitnami/redis:latest'
    environment:
    - REDIS_REPLICATION_MODE=slave
    - REDIS_MASTER_HOST=redis
    - ALLOW_EMPTY_PASSWORD=yes
    ports:
    - 6479:6379
    depends_on:
    - redis
    networks:
    - app-tier
    redis-slave-2:
    image: 'bitnami/redis:latest'
    environment:
    - REDIS_REPLICATION_MODE=slave
    - REDIS_MASTER_HOST=redis
    - ALLOW_EMPTY_PASSWORD=yes
    ports:
    - 6579:6379
    depends_on:
    - redis
    networks:
    - app-tier  
    ```

## Build
- Local
  ```
  :base-api:clean :base-api:build -Pprofile=local_h2 -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@
  ```
- Not Local
  ```
  :base-api:clean :base-api:build -Pprofile=production -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@
  ```

## App Start
- Local
  - Spring Boot Application Run => ApiApplication.java
- Not Local
  ```
  nohup java -jar base-api-0.0.0.jar -spring.profiles.active=production -Dfile.encoding=UTF-8 -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@ -Xms256M -Xmx512M -XX:OnOutOfMemoryError=\"kill -9 %p\" -XX:+HeapDumpOnOutOfMemoryError &
  ```

## App Test
- Intergration Test
- Unit Test
- Acceptance Test

## App Test VM Options
```
    gradle -Pprofile=local_h2 -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@
```  
