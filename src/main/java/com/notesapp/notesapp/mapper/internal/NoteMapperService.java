package com.notesapp.notesapp.mapper.internal;

import com.notesapp.notesapp.dto.NoteDto;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteMapperService implements NoteMapper {
    
    @Override
    public NoteDto mapNoteToNoteDto(Note note) {
        return NoteDto.builder()
                .title(note.getTitle())
                .content(note.getContent())
                .build();
    }
}
