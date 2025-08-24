package com.dulo.chat_platform.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {DateOfBirthValidator.class})
public @interface DateOfBirth {

    String message() default "Age must greater than or equal to 13 years";

    Class[] groups() default {};
    Class[] payload() default {};
}
