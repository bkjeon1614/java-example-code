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
    java -jar -Dspring.profiles.active=local -Dfile.encoding=UTF-8 -Xms256M -Xmx512M
```
