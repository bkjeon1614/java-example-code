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
    Class<?>[] groups() default {};
    Class<? extends EnumTypeValueInterface> target();
    Class<? extends Payload>[] payload() default {};

}
