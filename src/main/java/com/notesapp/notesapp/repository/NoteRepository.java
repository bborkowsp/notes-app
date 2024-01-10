package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.Note;
import com.notesapp.notesapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByAuthor(User author);

    List<Note> findAllByIsPublicIsTrue();
}
