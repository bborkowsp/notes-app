package com.notesapp.notesapp.mapper;

import com.notesapp.notesapp.dto.PublicNoteDto;
import com.notesapp.notesapp.dto.UpdateNoteDto;
import com.notesapp.notesapp.dto.UserNoteDto;
import com.notesapp.notesapp.model.Note;

public interface NoteMapper {
    UserNoteDto mapNoteToNoteDto(Note note);

    PublicNoteDto mapNoteToPublicNoteDto(Note note);

    Note mapUpdateNoteDtoToNote(UpdateNoteDto updateNoteDto);
}
