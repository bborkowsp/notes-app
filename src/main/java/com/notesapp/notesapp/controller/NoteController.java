package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.service.NoteUseCases;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class NoteController {

    private final NoteUseCases noteUseCases;

    @GetMapping("/my-notes")
    public String getAllNotes(Model model, @AuthenticationPrincipal User user) {
        final var notes = noteUseCases.getAllUserNotes(user);
        model.addAttribute("notes", notes);
        return "user/notes";
    }

    @GetMapping("/create")
    String showCreateNotePage(Model model) {
        model.addAttribute("createNoteDto", new CreateNoteDto("", "", null, false));
        return "user/create-note";
    }

    @PostMapping("/create")
    String createNote(@Valid CreateNoteDto createNoteDto, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
        System.out.println(createNoteDto);
        if (bindingResult.hasErrors()) {
            return "user/create-note";
        }
        try {
            noteUseCases.createNote(createNoteDto, user);
        } catch (Exception exception) {
            model.addAttribute("error", exception.getMessage());
            return "user/create-note";
        }
        return "redirect:/notes/my-notes";
    }


}

