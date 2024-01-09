package com.notesapp.notesapp.mapper;

import com.notesapp.notesapp.dto.NoteDto;
import com.notesapp.notesapp.model.Note;

public interface NoteMapper {
    NoteDto mapNoteToUserNoteDto(Note note);
}
