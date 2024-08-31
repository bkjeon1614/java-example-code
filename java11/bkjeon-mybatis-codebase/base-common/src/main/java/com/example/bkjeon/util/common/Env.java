package com.example.bkjeon.util.common;

import java.util.Locale;

/**
 * 시스템의 환경 변수 값을 가져올 때 사용한다.
 * 기존 소스 코드에 "/home/bkjeon/CACHE-INF" 로 되어 있는 부분이 있다면, 아래와 같이 변경하여야 한다.<p>
 * <code>
 * new File(Env.HOME_DIR, "CACHE-INF");
 * </code>
 * <p>
 * 또한, 코드 내에서 디렉토리 구분자인 '/'의 사용은 지양하고, File.separator를 사용하도록 한다.
 */
public final class Env {
    /**
     * 내부 생성자 (호출되지 않음)
     */
    private Env() {
        throw new UnsupportedOperationException();
    }

    /**
     * 사용자 홈 디렉토리
     */
    public static final String HOME_DIR = System.getProperty("user.home");

    /**
     * 임시 디렉토리
     */
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * 기본 로케일 (KOREAN)
     */
    public static final Locale DEFAULT_LOCALE = Locale.KOREAN;

    /**
     * 기본 인코딩 (UTF-8)
     */
    public static final String DEFAULT_ENCODING = "UTF-8";
}
