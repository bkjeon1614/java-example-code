package com.example.bkjeon.util.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ValidationUtil extends ValidationUtils {

    private final MessageSourceAccessor messageSourceAccessor;
    private static MessageSourceAccessor errorMessageSourceAccessor;

    @PostConstruct
    private void initMessageSourceAccessor() {
        errorMessageSourceAccessor = this.messageSourceAccessor;
    }
    public static List<String> getAllErrorMessages(BindingResult errors) {
        if (!errors.hasErrors()) {
            return null;
        }
        return errors.getAllErrors()
                .stream()
                .map(objectError -> errorMessageSourceAccessor.getMessage(objectError)).collect(Collectors.toList());
    }

    public static String getFirstErrorMessage(BindingResult errors) {
        if (!errors.hasErrors()) {
            return null;
        }
        return errorMessageSourceAccessor.getMessage(errors.getAllErrors().get(0));
    }

}
