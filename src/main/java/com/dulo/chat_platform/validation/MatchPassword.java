package com.dulo.chat_platform.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MatchPasswordValidator.class})
public @interface MatchPassword {

    String password();
    String confirmPassword();

    String message() default "Passwords do not match";
    Class<?>[] groups() default {};
    Class<?>[] payload() default {};
}
