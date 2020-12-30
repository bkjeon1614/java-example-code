# API Code Base

## Info
- Application: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html

## Build
```
    gradle fullBuild
```

## App Start
```
    java -jar -Dspring.profiles.active=local -Dfile.encoding=UTF-8 -Djasypt.encryptor.password=bkjeon!@ -Xms256M -Xmx512M
```