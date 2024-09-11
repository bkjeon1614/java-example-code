package com.example.bkjeon.base.services.api.v1.security;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("v1/security/encryption")
public class EncryptionController {

    /**
     * Jasypt 를 활용한 Application Property 암호화
     * 1. http://www.jasypt.org/download.html 에서 jasypt 다운 (1.9.2 버전 기준)
     * 2. 압축 풀고 cmd에서 bin 폴더로 이동 (./file/jasypt-1.9.3/bin)
     * 3. encrypt input="bkjeontest!@" password="bkjeon!@" algorithm="PBEWITHMD5ANDDES" (온라인 암/복호화 사이트 https://www.devglan.com/online-tools/jasypt-online-encryption-decryption)
     *    - Output) ig/i7xWXbv9Ss+GIQFm7Vk2RwUlQRBTb -> application.yml 에 정의 되어 있음 ENC라고 괄호안에 암호화된 패스워드를 넣으면 된다. (ENC로 해야 암호화된 문자열을 인식한다)
     *    - 또는 해당 API에서 encrypt된 값으로도 사용가능
     *    - 1~3 encrypt 과정으로 해도되고 해당 sample code에서 제공하는 EncryptionController 에서 encrypt 값을 사용해도 된다.
     * 4. gradle 의존성 추가
     *    ...
     *        // jasypt encrypt
     *        compile group: 'com.github.ulisesbocchio', name: 'jasypt-spring-boot-starter', version: '3.0.3'
     *    ...
     *    -> 만약 오류가 나면 spring 버전이나 jdk 버전 호환여부를 확인하자
     * 5. application.yml 에 내용 추가 (jasypt bean 활성화)
     *    ...
     *        jasypt:
     *          encryptor:
     *            bean: jasyptStringEncryptor
     *    ...
     * 6. ./config/jasyptEncryptConfig.java 생성
     * 7. passwordKey 는 VM Option 에 넣어준다. ( -Djasypt.encryptor.password=bkjeon!@ )
     */
    @ApiOperation("Jasypt를 사용한 properties 암호화")
    @GetMapping("jasyptEncrypt")
    public void getJasyptEncryptProperties() {
        try {
            StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
            pbeEnc.setPassword(System.getProperty("jasypt.encryptor.password"));
            pbeEnc.setAlgorithm("PBEWithMD5AndDES");

            // 암호화, 복호화
            String encrypt = pbeEnc.encrypt("wjsqhdrms");
            String decrypt = pbeEnc.decrypt(encrypt);
            log.info("encrypt: {}, decrypt: {}", encrypt, decrypt);
        } catch (Exception e) {
            log.error("getJasyptEncryptProperties ERROR {}", e.getMessage());
        }
    }
}
