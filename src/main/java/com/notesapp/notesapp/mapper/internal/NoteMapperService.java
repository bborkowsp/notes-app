package com.notesapp.notesapp.mapper.internal;

import com.notesapp.notesapp.dto.NoteDto;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.model.Note;
import org.springframework.stereotype.Service;

@Service
class NoteMapperService implements NoteMapper {

    @Override
    public NoteDto mapNoteToNoteDto(Note note) {
        return NoteDto.builder()
                .noteId(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .isEncrypted(note.getIsEncrypted())
                .isPublic(note.getIsPublic())
                .build();
    }
}
