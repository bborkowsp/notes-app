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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthController {

    private final AuthUseCases authUseCases;

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        System.out.println(error);
        if (error != null) {
            if (error.equals("maxAttempts")) {
                System.out.println("maxAttempts");
                model.addAttribute("errorMessage", "You have reached the maximum number of unsuccessful login attempts. Please try again later.");
            } else {
                model.addAttribute("errorMessage", "Invalid login credentials");
            }
        }
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
            return handleRegistrationErrors(registerUserDto, model);
        }

        try {
            authUseCases.register(registerUserDto);
            return handleSuccessfulRegistration(registerUserDto, redirectAttributes);
        } catch (IllegalStateException exception) {
            return handleFailedRegistration(model, exception);
        }
    }

    private String handleRegistrationErrors(RegisterUserDto registerUserDto, Model model) {
        model.addAttribute("registerUserDto", registerUserDto);
        return "register";
    }

    private String handleSuccessfulRegistration(RegisterUserDto registerUserDto, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("username", registerUserDto.username());
        return "redirect:/qrcode/{username}";
    }

    private String handleFailedRegistration(Model model, IllegalStateException exception) {
        model.addAttribute("error", exception.getMessage());
        return "register";
    }
}
