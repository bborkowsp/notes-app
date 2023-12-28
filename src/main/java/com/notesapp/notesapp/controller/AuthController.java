package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user) {
        System.out.println(user);
        return "redirect:/login";
    }
}
