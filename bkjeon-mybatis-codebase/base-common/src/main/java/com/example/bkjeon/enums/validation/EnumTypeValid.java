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
    Class<? extends EnumTypeValueInterface> target();

    // 하기 groups, payload 를 추가하지 않으면 javax.validation.ConstraintDefinitionException: HV000074 오류 발생
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
