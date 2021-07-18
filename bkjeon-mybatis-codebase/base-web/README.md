# API Code Base

## Info
- Application: http://localhost:9090
- Swagger: http://localhost:9090/swagger-ui.html

## Build
```
    gradle yarnBuild
```

## App Start
- local
  - WebApplication Start
- dev  
  ```
  $ nohup ~/apps/java/bin/java -Denv=dev -Dserver.port=9091 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=jvm.hprof -XX:OnOutOfMemoryError='kill -9 %p' -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@ -Djavax.net.ssl.keyStoreType=pkcs12 -Xmx2048m -Xms1024m -Dfile.encoding=UTF-8 -jar ~/deploy/bkjeon/base-web-0.0.0.jar &
  ```
- production
  ```
  
  ```
  
## App Test
```
    gradle -Pprofile=local -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@
```  
