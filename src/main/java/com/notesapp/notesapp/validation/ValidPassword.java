package com.notesapp.notesapp.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = CustomPasswordValidator.class)
public @interface ValidPassword {
    int MAX_PASSWORD_LENGTH = 120;
    int MIN_PASSWORD_LENGTH = 8;

    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

