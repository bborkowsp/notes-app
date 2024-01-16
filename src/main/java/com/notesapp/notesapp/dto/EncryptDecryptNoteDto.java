package com.notesapp.notesapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.notesapp.notesapp.validation.ValidPassword.MAX_PASSWORD_LENGTH;

public record EncryptDecryptNoteDto(
        Long encryptDecryptNoteId,

        @NotBlank(message = "Password cannot be blank")
        @Size( max = MAX_PASSWORD_LENGTH,
                message = "Password must not have more than " + MAX_PASSWORD_LENGTH + " characters")
        String password
) {
}
