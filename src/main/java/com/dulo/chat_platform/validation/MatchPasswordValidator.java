package com.dulo.chat_platform.validation;

import com.dulo.chat_platform.dto.request.RegistrationRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, RegistrationRequest> {

    @Override
    public void initialize(MatchPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(RegistrationRequest value, ConstraintValidatorContext context) {
        if(value.getPassword().isEmpty() || value.getConfirmPassword().isEmpty()) return false;
        return value.getPassword().equals(value.getConfirmPassword());
    }
}
