package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.dto.NoteDto;
import com.notesapp.notesapp.mapper.NoteMapper;
import com.notesapp.notesapp.model.Note;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.NoteRepository;
import com.notesapp.notesapp.service.NoteUseCases;
import lombok.RequiredArgsConstructor;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class NoteService implements NoteUseCases {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Override
    public List<NoteDto> getAllUserNotes(User user) {
        return noteRepository.findAllByAuthor(user).stream()
                .map(noteMapper::mapNoteToNoteDto)
                .toList();
    }

    @Override
    public void createNote(CreateNoteDto createNoteDto, User user) throws Exception {
        final var sanitizedTitle = sanitizeHtml(createNoteDto.title());
        final var sanitizedContent = sanitizeHtml(createNoteDto.content());
        final var password = createNoteDto.password();
        final var isEncrypted = password != null && !password.isBlank();
        final var title = isEncrypted ? NoteEncryptionService.encrypt(password, sanitizedTitle) : sanitizedTitle;
        final var content = isEncrypted ? NoteEncryptionService.encrypt(password, sanitizedContent) : sanitizedContent;
        final var note = new Note(title, content, user, createNoteDto.isPublic(), isEncrypted);
        validateNoteIsNotEncryptedAndPublic(note);
        noteRepository.save(note);
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
