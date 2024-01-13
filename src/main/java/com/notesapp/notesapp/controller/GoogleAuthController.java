package com.notesapp.notesapp.controller;


import com.google.zxing.WriterException;
import com.notesapp.notesapp.service.GoogleAuthUseCases;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GoogleAuthController {

    private final GoogleAuthenticator googleAuthenticator;
    private final GoogleAuthUseCases googleAuthUseCases;

    @GetMapping("/qrcode/{username}")
    public String showQRCodePage(@PathVariable String username, Model model) throws WriterException, IOException {
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);
        String imageBase64 = googleAuthUseCases.generateQRCodeImageBase64(key, username);

        model.addAttribute("imageBase64", imageBase64);
        model.addAttribute("username", username);

        return "qrcode";
    }


}