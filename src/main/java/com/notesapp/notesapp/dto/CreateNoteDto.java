package com.notesapp.notesapp.dto;

public record CreateNoteDto(
        String title,
        String content,
        String password,
        Boolean isPublic,
        Boolean isEncrypted
) {

}
