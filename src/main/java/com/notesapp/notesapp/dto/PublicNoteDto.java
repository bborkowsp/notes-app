package com.notesapp.notesapp.dto;

import lombok.Builder;

@Builder
public record PublicNoteDto(
        Long noteId,
        String author,
        String title,
        String content,
        Boolean isEncrypted,
        Boolean isPublic
) {
}
