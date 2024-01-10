package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.*;
import com.notesapp.notesapp.model.User;

import java.util.List;

public interface NoteUseCases {
    List<UserNoteDto> getAllUserNotes(User user);

    List<PublicNoteDto> getAllPublicNotes(User user);

    UserNoteDto getNoteToEdit(Long id, User user);

    void createNote(CreateNoteDto createNoteDto, User user) throws Exception;

    void updateNote(Long id, UpdateNoteDto updateNoteDto, User user) throws Exception;

    void deleteNote(Long id, User user);

    void encryptOrDecrypt(EncryptDecryptNoteDto encryptDecryptNoteDto, User user) throws Exception;
}
