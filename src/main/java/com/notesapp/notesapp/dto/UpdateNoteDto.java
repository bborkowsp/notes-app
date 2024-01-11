package com.notesapp.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.notesapp.notesapp.validation.ValidPassword.MAX_PASSWORD_LENGTH;

public record UpdateNoteDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 255, message = "Title must not have more than 255 characters")
        String title,

        @NotBlank(message = "Content cannot be blank")
        @Size(max = 5000, message = "Content must not have more than 5000 characters")
        String content,

        @Size(max = MAX_PASSWORD_LENGTH, message = "Password must not have more than" + MAX_PASSWORD_LENGTH + "characters")
        String password,

        Boolean isPublic
) {
}
