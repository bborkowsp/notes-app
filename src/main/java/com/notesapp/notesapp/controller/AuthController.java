package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.LoginUserDto;
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

    @PostMapping("/login")
    public String login(@ModelAttribute LoginUserDto loginUserDto, RedirectAttributes redirectAttributes) {
        authUseCases.login(loginUserDto);
        redirectAttributes.addAttribute("username", loginUserDto.username());
        return "redirect:/{username}/notes";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterUserDto registerUserDto, RedirectAttributes redirectAttributes) {
        authUseCases.register(registerUserDto);
        redirectAttributes.addAttribute("username", registerUserDto.username());
        return "redirect:/qrcode/{username}";
    }
}
