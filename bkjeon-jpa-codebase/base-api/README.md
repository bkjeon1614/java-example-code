# API Code Base

## Info
- H2 DB: http://localhost:8080/h2-console
  - jdbc url: jdbc:h2:mem:testdb
- Application: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## Build
```
    gradle fullBuild
```

## App Start
```
    java -jar base-api-0.0.0.jar --spring.profiles.active=dev -Dfile.encoding=UTF-8 -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@ -Xms256M -Xmx512M -XX:OnOutOfMemoryError=\"kill -9 %p\" -XX:+HeapDumpOnOutOfMemoryError
```

## App Test
```
    gradle -Pprofile=local_dev -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@
```
