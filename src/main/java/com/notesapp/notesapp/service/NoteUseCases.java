package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.dto.NoteDto;
import com.notesapp.notesapp.model.User;

import java.util.List;

public interface NoteUseCases {
    List<NoteDto> getAllUserNotes(User user);

    void createNote(CreateNoteDto createNoteDto, User user) throws Exception;
}
