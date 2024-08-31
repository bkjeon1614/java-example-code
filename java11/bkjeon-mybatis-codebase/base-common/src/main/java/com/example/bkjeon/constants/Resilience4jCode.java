package com.example.bkjeon.constants;

/**
 * Resilience4j 상수 선언 (서킷브레이커 관련 모듈 네임 매핑 용도)
 */
public final class Resilience4jCode {

    public static final String RETRY_TEST_3000 = "retry-test-3000";
    public static final String RETRY_DB_SELECT_4000 = "retry-db-select-4000";
    public static final String RETRY_DB_SELECT_5000 = "retry-db-select-5000";
    public static final String CIRCUIT_TEST_70000 = "circuit-test-70000";
    public static final String CIRCUIT_DB_SELECT_200 = "circuit-db-select-200";
    public static final String CIRCUIT_DB_SELECT_300 = "circuit-db-select-300";

}