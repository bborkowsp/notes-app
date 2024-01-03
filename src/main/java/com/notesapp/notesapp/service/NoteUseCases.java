package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.NoteDto;

import java.util.List;

public interface NoteUseCases {
    List<NoteDto> getAllNotes();
}
