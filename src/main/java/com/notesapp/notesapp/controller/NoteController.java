package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.dto.EncryptDecryptNoteDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class NoteController {

    private final NoteUseCases noteUseCases;

    @GetMapping("/my-notes")
    public String getAllNotesAndShowPage(Model model, @AuthenticationPrincipal User user) {
        final var notes = noteUseCases.getAllUserNotes(user);
        model.addAttribute("notes", notes);
        model.addAttribute("encryptDecryptNoteDto", new EncryptDecryptNoteDto(null, ""));
        return "user/notes";
    }

    @GetMapping("/create")
    String showCreateNotePage(Model model) {
        model.addAttribute("createNoteDto", new CreateNoteDto("", "", null, false));
        return "user/create-note";
    }

    @PostMapping("/create")
    String createNote(@Valid CreateNoteDto createNoteDto, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
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

    @GetMapping("/delete/{id}")
    String deleteNote(@PathVariable Long id, @AuthenticationPrincipal User user) {
        noteUseCases.deleteNote(id, user);
        return "redirect:/notes/my-notes";
    }

    @PostMapping("/encrypt-decrypt")
    String encryptOrDecryptNote(@Valid EncryptDecryptNoteDto encryptDecryptNoteDto, @AuthenticationPrincipal User user, BindingResult bindingResult) {
        System.out.println("-----------------");
        System.out.println(encryptDecryptNoteDto.getEncryptDecryptNoteId());
        try {
            noteUseCases.encryptOrDecrypt(encryptDecryptNoteDto, user);
            return "redirect:/notes/my-notes";
        } catch (Exception exception) {
            return "redirect:/notes/my-notes?error=" + exception.getMessage();
        }

    }

}

