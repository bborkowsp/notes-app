package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.NoteDto;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.repository.NoteRepository;
import com.notesapp.notesapp.service.NoteUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService implements NoteUseCases {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public List<NoteDto> getAllNotes() {
        final var notes = noteRepository.findAll();
        return notes.stream()
                .map(noteMapper::mapNoteToNoteDto)
                .toList();
    }
}
