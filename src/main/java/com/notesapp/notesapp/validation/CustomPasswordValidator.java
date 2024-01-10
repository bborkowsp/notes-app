package com.notesapp.notesapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.Arrays;

import static com.notesapp.notesapp.validation.ValidPassword.MAX_PASSWORD_LENGTH;
import static com.notesapp.notesapp.validation.ValidPassword.MIN_PASSWORD_LENGTH;

class CustomPasswordValidator implements ConstraintValidator<ValidPassword, String> {


    @Override
    public void initialize(final ValidPassword arg0) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        PasswordValidator validator = createPasswordValidator();
        RuleResult result = validator.validate(new PasswordData(password));

        if (result.isValid()) {
            return true;
        }
        handleInvalidPassword(context, validator, result);
        return false;
    }

    private PasswordValidator createPasswordValidator() {
        return new PasswordValidator(Arrays.asList(
                new LengthRule(MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()));
    }

    private void handleInvalidPassword(ConstraintValidatorContext context, PasswordValidator validator, RuleResult result) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.join(" ", validator.getMessages(result)))
                .addConstraintViolation();
    }
}
