package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.RegisterUserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(RegisterUserDto registerUserDto, Model model) {
        model.addAttribute("registerUserDto", registerUserDto);
        return "register";
    }

}
