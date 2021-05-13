package com.example.bkjeon.enums.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<EnumTypeValid, String> {

    private List<String> types;

    /**
     * 파라미터로 실제 클래스에 사용된 @EnumTypeValid Annotation이 매핑되어 target에는 EnumType.class 타입이
     * 존재하게 된다. 해당 타입을 얻었으니 전체 enum 상수 constants 들을 얻을 수 있고
     * 이를 통해서 모든 타입들을 얻을 수 있다.
     */
    @Override
    public void initialize(EnumTypeValid constraintAnnotation) {
        types = Arrays.stream(constraintAnnotation.target().getEnumConstants())
            .map(constant -> (constant).getTypeValue())
            .collect(Collectors.toList());
    }

    /**
     * 실제 유효성 판단을 내리는 곳으로 initialize를 통해서 타입들을 얻었으니 contains 메소드를 활용해서
     * 현재 파라미터 클래스의 enum 타입 변수로 들어온 값이 types에 존재하는지만 판단하면 된다.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return types.contains(value);
    }


}
