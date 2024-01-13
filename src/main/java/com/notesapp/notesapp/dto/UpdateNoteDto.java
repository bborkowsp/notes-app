package com.notesapp.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static com.notesapp.notesapp.validation.ValidPassword.MAX_PASSWORD_LENGTH;
import static com.notesapp.notesapp.validation.ValidPassword.MIN_PASSWORD_LENGTH;

public record UpdateNoteDto(
        @NotBlank(message = "Title cannot be blank")
        @Size(max = 255, message = "Title must not have more than 255 characters")
        String title,

        @Size(max = 5000, message = "Content is too long")
        String content,

        @Size(min = MIN_PASSWORD_LENGTH, max = MAX_PASSWORD_LENGTH,
                message = "Password must be between " + MIN_PASSWORD_LENGTH + " and " + MAX_PASSWORD_LENGTH + " characters")
        String password,

        Boolean isPublic
) {
}
