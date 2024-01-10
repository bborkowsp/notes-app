package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.*;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.model.Note;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.NoteRepository;
import com.notesapp.notesapp.service.NoteUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.owasp.html.Sanitizers;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class NoteService implements NoteUseCases {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserNoteDto> getAllUserNotes(User user) {
        return noteRepository.findAllByAuthor(user).stream()
                .map(noteMapper::mapNoteToNoteDto)
                .toList();
    }

    @Override
    public List<PublicNoteDto> getAllPublicNotes(User user) {
        return noteRepository.findAllByIsPublicIsTrue().stream()
                .filter(note -> !note.getAuthor().equals(user))
                .map(noteMapper::mapNoteToPublicNoteDto)
                .toList();
    }

    @Override
    public void createNote(CreateNoteDto createNoteDto, User user) throws Exception {
        final var sanitizedTitle = sanitizeHtml(createNoteDto.title());
        final var sanitizedContent = sanitizeHtml(createNoteDto.content());
        final var isEncrypted = createNoteDto.password() != null && !createNoteDto.password().isBlank();
        final var password = sanitizeHtml(createNoteDto.password());
        final var title = isEncrypted ? NoteEncryptionService.encrypt(password, sanitizedTitle) : sanitizedTitle;
        final var content = isEncrypted ? NoteEncryptionService.encrypt(password, sanitizedContent) : sanitizedContent;
        final var encodedPassword = passwordEncoder.encode(password);
        final var note = new Note(title, content, user, encodedPassword, createNoteDto.isPublic(), isEncrypted);
        validateNoteIsNotEncryptedAndPublic(note);
        noteRepository.save(note);
    }

    @Override
    public void updateNote(Long id, UpdateNoteDto updateNoteDto, User user) throws Exception {
        final var note = noteRepository.findById(id).orElseThrow();
        validateUserIsNoteAuthor(user, note);
        final var sanitizedTitle = sanitizeHtml(updateNoteDto.title());
        final var sanitizedContent = sanitizeHtml(updateNoteDto.content());
        final var isEncrypted = updateNoteDto.password() != null && !updateNoteDto.password().isBlank();
        final var password = sanitizeHtml(updateNoteDto.password());
        final var title = isEncrypted ? NoteEncryptionService.encrypt(password, sanitizedTitle) : sanitizedTitle;
        final var content = isEncrypted ? NoteEncryptionService.encrypt(password, sanitizedContent) : sanitizedContent;
        final var encodedPassword = passwordEncoder.encode(password);
        note.setTitle(title);
        note.setContent(content);
        note.setPassword(encodedPassword);
        note.setIsPublic(updateNoteDto.isPublic());
        note.setIsEncrypted(isEncrypted);
        validateNoteIsNotEncryptedAndPublic(note);
        noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id, User user) {
        final var note = noteRepository.findById(id).orElseThrow();
        validateUserIsNoteAuthor(user, note);
        noteRepository.delete(note);
    }

    @Override
    public void encryptOrDecrypt(EncryptDecryptNoteDto encryptDecryptNoteDto, User user) throws Exception {
        validatePasswordLength(encryptDecryptNoteDto.password());
        final var note = noteRepository.findById(encryptDecryptNoteDto.encryptDecryptNoteId()).orElseThrow();
        validateUserIsNoteAuthor(user, note);
        final var password = sanitizeHtml(encryptDecryptNoteDto.password());

        if (note.getIsEncrypted()) {
            decryptNote(note, password);
        } else {
            encryptNote(note, password);
        }
    }

    @Override
    public UserNoteDto getNoteToEdit(Long id, User user) {
        final var note = noteRepository.findById(id).orElseThrow();
        validateNoteIsNotEncrypted(note);
        return noteMapper.mapNoteToNoteDto(note);
    }


    private void validateNoteIsNotEncrypted(Note note) {
        if (note.getIsEncrypted()) {
            throw new IllegalArgumentException("Cannot edit encrypted note");
        }
    }

    private void validatePasswordLength(String password) {
        if (password.length() > 120 || password.length() < 8) {
            throw new IllegalArgumentException("Password must be between 8 and 120 characters long");
        }
    }

    private void validatePasswordIsCorrect(String password, Note note) {
        if (!passwordEncoder.matches(password, note.getPassword())) {
            throw new IllegalArgumentException("Incorrect decryption password");
        }
    }

    private void validateUserIsNoteAuthor(User user, Note note) {
        if (!note.getAuthor().equals(user)) {
            throw new IllegalArgumentException("User is not authorized to perform this action");
        }
    }

    private void validateNoteIsNotEncryptedAndPublic(Note note) {
        if (note.getIsPublic() && note.getIsEncrypted()) {
            throw new IllegalArgumentException("Cannot create public encrypted note");
        }
    }

    private void decryptNote(Note note, String password) throws Exception {
        validatePasswordIsCorrect(password, note);
        final var decryptedTitle = NoteEncryptionService.decrypt(note.getTitle(), password);
        final var decryptedContent = NoteEncryptionService.decrypt(note.getContent(), password);
        updateNoteAfterDecryption(note, decryptedTitle, decryptedContent);
    }

    private void encryptNote(Note note, String password) throws Exception {
        final var encryptedTitle = NoteEncryptionService.encrypt(password, note.getTitle());
        final var encryptedContent = NoteEncryptionService.encrypt(password, note.getContent());
        final var encodedPassword = passwordEncoder.encode(password);
        updateNoteAfterEncryption(note, encryptedTitle, encryptedContent, encodedPassword);
    }

    private void updateNoteAfterDecryption(Note note, String decryptedTitle, String decryptedContent) {
        note.setTitle(decryptedTitle);
        note.setContent(decryptedContent);
        note.setIsEncrypted(false);
    }

    private void updateNoteAfterEncryption(Note note, String encryptedTitle, String encryptedContent, String encodedPassword) {
        note.setTitle(encryptedTitle);
        note.setContent(encryptedContent);
        note.setPassword(encodedPassword);
        note.setIsEncrypted(true);
    }

    private String sanitizeHtml(String htmlText) {
        final var policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.IMAGES).and(Sanitizers.BLOCKS);
        return policy.sanitize(htmlText);
    }
}
