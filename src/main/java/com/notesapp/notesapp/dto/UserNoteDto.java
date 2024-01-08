package com.notesapp.notesapp.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record UserNoteDto(
        Long id,
        String title,
        String content,
        Boolean isEncrypted,
        Boolean isPublic
) {
}
