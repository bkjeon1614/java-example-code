package com.example.bkjeon.base.validation;

import com.example.bkjeon.dto.validation.PostValidDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class PostValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PostValidDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PostValidDTO postValidDTO = (PostValidDTO) target;

        if (Objects.isNull(postValidDTO.getName())) {
            errors.reject("postValidDTO.name.empty");
            return;
        }
    }

}
