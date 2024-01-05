package com.notesapp.notesapp.validation;

import com.notesapp.notesapp.dto.RegisterUserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

class ConfirmationPasswordValidator implements ConstraintValidator<ValidConfirmationPassword, Object> {

    @Override
    public void initialize(final ValidConfirmationPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final RegisterUserDto user = (RegisterUserDto) obj;
        return user.password().equals(user.matchingPassword());
    }

}
