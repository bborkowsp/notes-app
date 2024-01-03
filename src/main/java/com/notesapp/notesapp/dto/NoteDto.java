package com.notesapp.notesapp.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Builder
@Jacksonized
public record NoteDto(
        String title,
        String content) {
}
