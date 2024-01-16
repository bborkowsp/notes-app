package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.service.AuthUseCases;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.WebAttributes;
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
    public String loginPage(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            AuthenticationException authenticationException = (AuthenticationException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (authenticationException != null) {
                errorMessage = authenticationException.getMessage();
            }
        }
        model.addAttribute("errorMessage", errorMessage);
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

    @GetMapping("/account-activity")
    String showLastSuccessfulLoginsPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("userLoginActivityHistory", authUseCases.getUserAccountLoginActivityHistory(user));
        return "note/user-account-login-activity-history";
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
