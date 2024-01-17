package com.notesapp.notesapp.dto;

import com.notesapp.notesapp.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.notesapp.notesapp.validation.ValidPassword.MAX_PASSWORD_LENGTH;


public record RegisterUserDto(
        @NotBlank(message = "Username cannot be blank!")
        @Size(max = 255, message = "Username must not have more than 255 characters!")
        String username,

        @Email(message = "Email is in wrong format!", regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
        @NotBlank(message = "Email cannot be blank")
        @Size(max = 255, message = "Email must not have more than 255 characters")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @ValidPassword
        String password,

        @NotBlank(message = "Password confirmation cannot be blank")
        @Size(max = MAX_PASSWORD_LENGTH,
                message = "Password must not have more than " + MAX_PASSWORD_LENGTH + " characters")
        String matchingPassword
) {
}
