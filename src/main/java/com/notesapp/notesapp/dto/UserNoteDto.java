package com.notesapp.notesapp.dto;

import lombok.Builder;

@Builder

public record UserNoteDto(
        Long noteId,
        String title,
        String content,
        Boolean isEncrypted,
        Boolean isPublic
) {
}
