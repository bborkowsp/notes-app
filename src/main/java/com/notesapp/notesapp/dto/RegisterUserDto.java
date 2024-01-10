package com.notesapp.notesapp.dto;

import com.notesapp.notesapp.validation.ValidEmail;
import com.notesapp.notesapp.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;


@Builder
@Jacksonized
public record RegisterUserDto(
        @NotBlank
        @Size(min = 5, message = "Username is too short!")
        @Size(max = 30, message = "Username is too long!")
        String username,

        @Email @ValidEmail @NotBlank @Size(max = 255) String email,

        @NotBlank @ValidPassword String password,

        @NotBlank @Size(min = 1, max = 120, message = "Password must be no more than 120 characters in length.")
        String matchingPassword
) {
}
