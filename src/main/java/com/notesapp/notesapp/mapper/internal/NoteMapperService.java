package com.notesapp.notesapp.mapper.internal;

import com.notesapp.notesapp.dto.UserNoteDto;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.model.Note;
import org.springframework.stereotype.Service;

@Service
class NoteMapperService implements NoteMapper {

    @Override
    public UserNoteDto mapNoteToUserNoteDto(Note note) {
        return UserNoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .isEncrypted(note.getIsEncrypted())
                .isPublic(note.getIsPublic())
                .build();
    }
}
