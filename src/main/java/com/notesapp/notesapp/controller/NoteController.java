package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.service.NoteUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class NoteController {

    private final NoteUseCases noteUseCases;

    @GetMapping("/user/{username}/notes")
    public String getAllNotes(@PathVariable String username, Model model, Principal principal) {
        final var notes = noteUseCases.getAllNotes();
        model.addAttribute("notes", notes);
        model.addAttribute("username", username);
        return "/user/notes";
    }
}

