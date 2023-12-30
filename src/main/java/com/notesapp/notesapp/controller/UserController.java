package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.UserDto;
import com.notesapp.notesapp.service.UserUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserUseCases userUseCases;

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute UserDto userDto, RedirectAttributes redirectAttributes) {
        userUseCases.createUser(userDto);
        redirectAttributes.addAttribute("username", userDto.userName());
        return "redirect:/qrcode/{username}";
    }
}
