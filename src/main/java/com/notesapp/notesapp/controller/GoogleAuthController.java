package com.notesapp.notesapp.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class GoogleAuthController {

    private final GoogleAuthenticator googleAuthenticator;

    @GetMapping("/qrcode/{username}")
    public String showQRCode(@PathVariable String username, Model model) throws WriterException, IOException {
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        final var otpAuthTotpURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("notes-app", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthTotpURL, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String imageBase64 = Base64.encodeBase64String(bytes);

        model.addAttribute("imageBase64", imageBase64);
        model.addAttribute("username", username);

        return "qrcode";
    }

}