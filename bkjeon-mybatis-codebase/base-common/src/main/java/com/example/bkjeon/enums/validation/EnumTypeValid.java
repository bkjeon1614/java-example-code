package com.example.bkjeon.enums.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = EnumValidator.class)
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumTypeValid {

    String message() default "invalid parameter!!";

    /**
     * target 속성으로 EnumType.class 를 지정해주면 실제 bean validator에 의해 ConstraintValidator를 구현한 클래스들을 실행하고
     * intialize(), isValid() 의 실행순서를 가지면서 실행된다.
     */
    Class<? extends EnumTypeValueInterface> target();

    // 하기 groups, payload 를 추가하지 않으면 javax.validation.ConstraintDefinitionException: HV000074 오류 발생
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
