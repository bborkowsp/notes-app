package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.dto.UserNoteDto;
import com.notesapp.notesapp.model.User;

import java.util.List;

public interface NoteUseCases {
    List<UserNoteDto> getAllUserNotes(User user);

    void createNote(CreateNoteDto createNoteDto, User user) throws Exception;

    void deleteNote(Long id, User user);
}
