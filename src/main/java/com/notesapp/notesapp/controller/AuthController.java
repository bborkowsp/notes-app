package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.service.AuthUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCases authUseCases;

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterUserDto registerUserDto, RedirectAttributes redirectAttributes) {
        authUseCases.register(registerUserDto);
        redirectAttributes.addAttribute("username", registerUserDto.username());
        return "redirect:/qrcode/{username}";
    }
}
