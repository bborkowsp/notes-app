package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.dto.UserNoteDto;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.model.Note;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.NoteRepository;
import com.notesapp.notesapp.service.NoteUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class NoteService implements NoteUseCases {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public List<UserNoteDto> getAllUserNotes(User user) {
        return noteRepository.findAllByAuthor(user).stream()
                .map(noteMapper::mapNoteToUserNoteDto)
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
        final var note = new Note(title, content, user, createNoteDto.isPublic(), isEncrypted);
        validateNoteIsNotEncryptedAndPublic(note);
        System.out.println(note);
        noteRepository.save(note);
    }

    @Override
    public void deleteNote(Long id, User user) {
        final var note = noteRepository.findById(id).orElseThrow();
        System.out.println(note);
        validateUserIsNoteAuthor(user, note);
        noteRepository.delete(note);
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

    private String sanitizeHtml(String htmlText) {
        final var policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS).and(Sanitizers.IMAGES).and(Sanitizers.BLOCKS);
        return policy.sanitize(htmlText);
    }


}
