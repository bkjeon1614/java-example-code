package com.example.bkjeon.enums.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<EnumTypeValid, String> {

    private List<String> types;

    @Override
    public void initialize(EnumTypeValid constraintAnnotation) {
        types = Arrays.stream(constraintAnnotation.target().getEnumConstants())
            .map(constant -> (constant).getTypeValue())
            .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return types.contains(value);
    }


}
