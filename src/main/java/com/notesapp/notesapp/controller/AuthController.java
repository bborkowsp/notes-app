package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.service.AuthUseCases;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthController {

    private final AuthUseCases authUseCases;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerUserDto", new RegisterUserDto("", "", "", ""));
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid RegisterUserDto registerUserDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerUserDto", registerUserDto);
            return "register";
        }
        try {
            authUseCases.register(registerUserDto);
            redirectAttributes.addAttribute("username", registerUserDto.username());
            return "redirect:/qrcode/{username}";
        } catch (IllegalStateException exception) {
            model.addAttribute("error", exception.getMessage());
            return "register";
        }
    }
}
