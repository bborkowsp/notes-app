package com.notesapp.notesapp.dto;

import com.notesapp.notesapp.validation.ValidEmail;
import com.notesapp.notesapp.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.notesapp.notesapp.validation.ValidPassword.MAX_PASSWORD_LENGTH;
import static com.notesapp.notesapp.validation.ValidPassword.MIN_PASSWORD_LENGTH;


public record RegisterUserDto(
        @NotBlank
        @Size(min = 5, message = "Username is too short!")
        @Size(max = 30, message = "Username is too long!")
        String username,

        @Email
        @ValidEmail
        @NotBlank
        @Size(max = 255)
        String email,

        @NotBlank
        @ValidPassword
        String password,

        @NotBlank
        @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH,
                message = "Password must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " characters")
        String matchingPassword
) {
}
