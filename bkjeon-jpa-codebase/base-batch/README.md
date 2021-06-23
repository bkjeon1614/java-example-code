# Batch Code Base

## Batch Start
```
    java -jar -Dspring.profiles.active=local -Dfile.encoding=UTF-8 -Djasypt.encryptor.password=bkjeon!@ -Dmysql.aeskey=bkjeon!@ --job.name={jobName} version={versionNum} -Xms256M -Xmx512M
```