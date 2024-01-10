package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.*;
import com.notesapp.notesapp.model.User;

import java.util.List;

public interface NoteUseCases {
    List<NoteDto> getAllUserNotes(User user);

    void createNote(CreateNoteDto createNoteDto, User user) throws Exception;

    void deleteNote(Long id, User user);

    void encryptOrDecrypt(EncryptDecryptNoteDto encryptDecryptNoteDto, User user) throws Exception;

    NoteDto getNoteToEdit(Long id, User user);

    void updateNote(Long id, UpdateNoteDto updateNoteDto, User user) throws Exception;

    List<PublicNoteDto> getAllPublicNotes(User user);
}
