package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.CreateNoteDto;
import com.notesapp.notesapp.dto.EncryptDecryptNoteDto;
import com.notesapp.notesapp.dto.UpdateNoteDto;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.service.NoteUseCases;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        return "note/notes";
    }

    @GetMapping("/public-notes")
    public String getAllPublicNotesAndShowPage(Model model, @AuthenticationPrincipal User user) {
        try {
            final var notes = noteUseCases.getAllPublicNotes(user);
            model.addAttribute("notes", notes);
            model.addAttribute("encryptDecryptNoteDto", new EncryptDecryptNoteDto(null, ""));
            return "note/public-notes";
        } catch (IllegalArgumentException exception) {
            return handleNotesError(model, exception);
        }
    }

    @PostMapping("/encrypt-decrypt")
    String encryptOrDecryptNote(@Valid EncryptDecryptNoteDto encryptDecryptNoteDto, @AuthenticationPrincipal User user, Model model, HttpServletRequest request) {
        try {
            noteUseCases.encryptOrDecrypt(encryptDecryptNoteDto, user);
            return handleEncryptDecryptSuccess(request.getHeader("Referer"));
        } catch (Exception exception) {
            return handleEncryptDecryptError(model, exception, request.getHeader("Referer"));
        }
    }

    @GetMapping("/create")
    String showCreateNotePage(Model model) {
        model.addAttribute("createNoteDto", new CreateNoteDto("", "", null, false));
        return "note/create-note";
    }

    @PostMapping("/create")
    String createNote(@Valid CreateNoteDto createNoteDto, BindingResult bindingResult, @AuthenticationPrincipal User user, Model model) {
        if (bindingResult.hasErrors()) {
            return "note/create-note";
        }
        try {
            noteUseCases.createNote(createNoteDto, user);
        } catch (Exception exception) {
            return handleNoteCreationError(model, exception);
        }
        return "redirect:/notes/my-notes";
    }


    @GetMapping("/delete/{id}")
    String deleteNote(@PathVariable Long id, @AuthenticationPrincipal User user, Model model, HttpServletRequest request) {
        try {
            noteUseCases.deleteNote(id, user);
            return handleNoteDeleteSuccess(request.getHeader("Referer"));
        } catch (Exception exception) {
            return handleNoteDeleteError(model, exception, request.getHeader("Referer"));
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditPage(@PathVariable Long id, @AuthenticationPrincipal User user, Model model, HttpServletRequest request) {
        try {
            final var note = noteUseCases.getNoteToEdit(id, user);
            model.addAttribute("updateNoteDto", new UpdateNoteDto(note.title(), note.content(), null, note.isPublic()));
            model.addAttribute("noteId", id);
            return "note/edit-note";
        } catch (IllegalArgumentException exception) {
            return handleNoteEditError(model, exception, request.getHeader("Referer"));
        }
    }

    @PostMapping("/update/{id}")
    String updateNote(@PathVariable Long id, @Valid UpdateNoteDto updateNoteDto, BindingResult bindingResult, @AuthenticationPrincipal User user, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return handleUpdateNoteValidationError(id, updateNoteDto, bindingResult, redirectAttributes);
        }
        try {
            noteUseCases.updateNote(id, updateNoteDto, user);
        } catch (Exception exception) {
            return handleNoteUpdateError(model, exception);
        }
        return "redirect:/notes/my-notes";
    }

    private String handleEncryptDecryptSuccess(String referer) {
        return referer.contains("/notes/public-notes") ? "redirect:/notes/public-notes" : "redirect:/notes/my-notes";
    }

    private String handleEncryptDecryptError(Model model, Exception exception, String referer) {
        model.addAttribute("error", exception.getMessage());
        return referer.contains("/notes/my-notes") ? "redirect:/notes/my-notes?error=" + exception.getMessage() : "redirect:/notes/public-notes?error=" + exception.getMessage();
    }

    private String handleNotesError(Model model, IllegalArgumentException exception) {
        model.addAttribute("error", exception.getMessage());
        return "redirect:" + "/notes/my-notes" + "?error=" + exception.getMessage();
    }

    private String handleUpdateNoteValidationError(Long id, UpdateNoteDto updateNoteDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        for (FieldError error : bindingResult.getFieldErrors()) {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            redirectAttributes.addFlashAttribute(fieldName, errorMessage);
        }

        redirectAttributes.addFlashAttribute("updateNoteDto", updateNoteDto);
        return "redirect:/notes/edit/" + id;
    }


    private String handleNoteUpdateError(Model model, Exception exception) {
        model.addAttribute("error", exception.getMessage());
        return "note/edit-note";
    }

    private String handleNoteDeleteSuccess(String referer) {
        return referer.contains("/notes/public-notes") ? "redirect:/notes/public-notes" : "redirect:/notes/my-notes";
    }

    private String handleNoteDeleteError(Model model, Exception exception, String referer) {
        model.addAttribute("error", exception.getMessage());
        return referer.contains("/notes/my-notes") ? "redirect:/notes/my-notes?error=" + exception.getMessage() : "redirect:/notes/public-notes?error=" + exception.getMessage();
    }

    private String handleNoteCreationError(Model model, Exception exception) {
        model.addAttribute("error", exception.getMessage());
        return "note/create-note";
    }

    private String handleNoteEditError(Model model, IllegalArgumentException exception, String referer) {
        model.addAttribute("error", exception.getMessage());
        return referer.contains("/notes/my-notes") ? "redirect:/notes/my-notes?error=" + exception.getMessage() : "redirect:/notes/public-notes?error=" + exception.getMessage();
    }

}

