# API Code Base

## Info
- Application: http://localhost:9090
- Swagger: http://localhost:9090/api/swagger-ui.html
- Zipkin Dashboard: http://localhost:9411

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
