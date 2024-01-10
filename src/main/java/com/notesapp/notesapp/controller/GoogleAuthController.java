package com.notesapp.notesapp.controller;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GoogleAuthController {

    private final GoogleAuthenticator googleAuthenticator;

    @GetMapping("/qrcode/{username}")
    public String showQRCodePage(@PathVariable String username, Model model) throws WriterException, IOException {
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);
        String imageBase64 = generateQRCodeImageBase64(key, username);

        model.addAttribute("imageBase64", imageBase64);
        model.addAttribute("username", username);

        return "qrcode";
    }

    private String generateQRCodeImageBase64(GoogleAuthenticatorKey key, String username) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthTotpURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("notes-app", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthTotpURL, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeBase64String(bytes);
    }

}