package com.notesapp.notesapp.mapper;

import com.notesapp.notesapp.dto.UserNoteDto;
import com.notesapp.notesapp.model.Note;

public interface NoteMapper {
    UserNoteDto mapNoteToUserNoteDto(Note note);
}
