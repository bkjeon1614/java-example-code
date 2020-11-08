package com.example.bkjeon.base.services.api.v1.validation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;
import java.math.BigInteger;

@NoArgsConstructor
@Getter
@ToString
public class PostValidDTO {

    // ------------------------------------ 문자열 유무 검증 목록 start
    /**
     * @Message: 반드시 값이 있어야 합니다.
     */
    @NotNull
    private String name;

    /**
     * @Message: 이메일 주소가 유효하지 않습니다.
     */
    @Email
    private String email;

    /**
     * 어떤 타입이든 수용하며 Null 값 이다.
     * @Message: 반드시 값이 없어야 합니다.
     * @Example
     *   - null: valid
     *   - "": invalid
     *   - " ": invalid
     */
    @Null
    private String isNull;

    /**
     * 어떤 타입이든 수용하며 null 이 아닌 값이다.
     * @Message: 반드시 값이 있어야 한다.
     * @Example
     *   - null: invalid
     *   - "": valid
     *   - " ": valid
     */
    @NotNull
    private String notNull;

    /**
     * CharSequence, Collection, Map, Array
     * null 이거나 empty(빈 문자열)가 아니어야 한다.
     * @Message: 반드시 값이 존재하고 길이 혹은 크기가 0보다 커야한다.
     * @Example
     *   - null: invalid
     *   - "": invalid
     *   - " ": valid
     */
    @NotEmpty
    private String notEmpty;

    /**
     * null 이 아닌 값이며 공백이 아닌 문자를 하나 이상 포함한다
     * @Message: 반드시 값이 존재하고 공백 문자를 제외한 길이가 0보다 커야 한다.
     * @Example
     *   - null: invalid
     *   - "": invalid
     *   - " ": invalid
     */
    @NotBlank
    private String notBlank;
    // ------------------------------------ 문자열 유무 검증 목록 end

    // ------------------------------------ 최대 최소에 대한 검증 목록 start
    /**
     * @Message: 지정된 최대 값보다 작거나 같아야 한다. (String 사용 시)
     */
    @DecimalMax(value = "1000000000")
    private BigInteger decimalMax;

    /**
     * @Message: 지정된 최대 값보다 크거나 같아야 한다. (String 사용 시)
     */
    @DecimalMax(value = "1")
    private BigInteger decimalMin;

    /**
     * @Message: 지정된 최대 값보다 작거나 같아야 한다. (Integer 사용 시)
     */
    @Max(value = 1000)
    private Integer max;

    /**
     * @Message: 지정된 최대 값보다 크거나 같아야 한다. (Integer 사용 시)
     */
    @Max(value = 1)
    private Integer min;
    // ------------------------------------ 최대 최소에 대한 검증 목록 end

    // ------------------------------------ 범위값에 대한 검증 목록 start
    /**
     * 양수인 값이다. (null 도 valid 로 간주한다.)
     * @Message: must be greater than 0
     */
    @Positive
    private Integer positive;

    /**
     * 0이거나 양수인 값이다. (null 도 valid 로 간주한다.)
     * @Message: must be greater than or equal to 0
     */
    @PositiveOrZero
    private Integer positiveOrZero;

    /**
     * 음수인 값이다. (null 도 valid 로 간주한다.)
     * @Message: must be less than 0
     */
    @Negative
    private Integer negative;

    /**
     * 0이거나 음수인 값이다. (null 도 valid 로 간주한다.)
     * @Message: must be less than or equal to 0
     */
    @NegativeOrZero
    private Integer negativeOrZero;
    // ------------------------------------ 범위값에 대한 검증 목록 end

    // ------------------------------------ 자릿수에 대한 범위 검증 목록 start
    /**
     * 대상 수가 지정된 정수와 소수 자리수보다 적을 경우 통과 가능
     * - int integer  => 이 숫자에 허용되는 최대 정수 자릿수
     * - int fraction  => 이 숫자에 허용되는 최대 소수 자릿수
     * @Message: 숫자 값이 허용 범위를 벗어납니다. (허용 범위: <5 자리>.<5 자리>)
     */
    @Digits(integer = 5, fraction = 5)
    private Integer digits;
    // ------------------------------------ 자릿수에 대한 범위 검증 목록 end

    // ------------------------------------ Boolean 에 대한 범위 검증 목록 start
    /**
     * 값이 항상 True 여야 한다.
     * @Message: 반드시 참(true)이어야 합니다.
     */
    @AssertTrue
    private boolean assertTrue;

    /**
     * 값이 항상 False 여야 한다.
     * @Message: 반드시 거짓(false)이어야 합니다.
     */
    @AssertFalse
    private boolean assertFalse;
    // ------------------------------------ Boolean 에 대한 범위 검증 목록 end

    // ------------------------------------ 크기 검증 목록 start
    /**
     * CharSequence, Collection, Map, Array
     * 문자열의 자릿수가 지정된 경계(포함) 사이에 있어야 한다. (null 도 valid 로 간주한다.)
     *   - int max  => element의 크기가 작거나 같다.
     *   - int min  => element의 크기가 크거나 같다.
     * @Message: 반드시 최소값 3과(와) 최대값 5 사이의 크기이어야 합니다.
     */
    @Size(max = 5, min = 3)
    private String size;
    // ------------------------------------ 크기 검증 목록 end

    // ------------------------------------ 정규식 검증 목록 start
    // yyyy-mm-dd 형태를 가지는 패턴 조사
    @Pattern(regexp = "^(19|20)\\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$")
    private String pattern;
    // ------------------------------------ 정규식 검증 목록 end

}
