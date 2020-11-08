package com.example.bkjeon.feature.validation;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;

@NoArgsConstructor
@Getter
@ToString
public class PostValidDTO {

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
     * The annotated element must not be null nor empty. Supported types are:
     * CharSequence (length of character sequence is evaluated)
     * Collection (collection size is evaluated)
     * Map (map size is evaluated)
     * Array (array length is evaluated)
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

}
