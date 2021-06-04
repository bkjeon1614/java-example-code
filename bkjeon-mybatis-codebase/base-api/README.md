# API Code Base

## Info
- Application: http://localhost:8080
- Swagger: http://localhost:8080/swagger-ui.html
- jasypt decode: https://www.devglan.com/online-tools/jasypt-online-encryption-decryption
  - config/JasyptEncryptConfig.java

## Build
```
    gradle fullBuild
```

## App Start
```
    java -jar base-api-0.0.0.jar --spring.profiles.active=dev -Dfile.encoding=UTF-8 -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@ -Xms256M -Xmx512M -XX:OnOutOfMemoryError=\"kill -9 %p\" -XX:+HeapDumpOnOutOfMemoryError
```
