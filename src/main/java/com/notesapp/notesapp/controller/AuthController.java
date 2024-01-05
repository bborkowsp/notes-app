package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.service.AuthUseCases;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthUseCases authUseCases;

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUserDto") RegisterUserDto registerUserDto,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        System.out.println(bindingResult.toString());
        if (bindingResult.hasErrors()) {
            model.addAttribute("registerUserDto", registerUserDto);
            return "register";
        }
        authUseCases.register(registerUserDto);
        redirectAttributes.addAttribute("username", registerUserDto.username());
        return "redirect:/qrcode/{username}";
    }
}
