package com.notesapp.notesapp.dto;


import jakarta.validation.constraints.NotBlank;

public record EncryptDecryptNoteDto(
        Long encryptDecryptNoteId,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
