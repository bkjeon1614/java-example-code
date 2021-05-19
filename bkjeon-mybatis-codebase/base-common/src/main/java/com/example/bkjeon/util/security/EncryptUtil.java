package com.example.bkjeon.util.security;

import java.security.MessageDigest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptUtil {

    private EncryptUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String getStringToSHA256Encrypt(String planText) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(planText.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            log.error("getStringToSHA256Encrypt: {}, ", e);
            throw new RuntimeException();
        }
    }

}
