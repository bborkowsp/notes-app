package com.notesapp.notesapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EncryptDecryptNoteDto(
        Long encryptDecryptNoteId,
        @NotBlank(message = "Password cannot be blank")
        @Size(max = 120, message = "Password length cannot exceed 120 characters")
        String password
) {
}
