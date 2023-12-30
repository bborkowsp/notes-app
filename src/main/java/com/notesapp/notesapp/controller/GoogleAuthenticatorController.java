package com.notesapp.notesapp.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.notesapp.notesapp.dto.ValidationCodeDto;
import com.notesapp.notesapp.model.Validation;
import com.notesapp.notesapp.repository.CredentialRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoogleAuthenticatorController {

    private final GoogleAuthenticator googleAuthenticator;
    private final CredentialRepository credentialRepository;

    @GetMapping("/qrcode/{username}")
    public String showQRCode(@PathVariable String username, Model model) throws WriterException, IOException {
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(username);

        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        // Convert the BitMatrix to a Base64-encoded image
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        byte[] bytes = baos.toByteArray();
        String imageBase64 = Base64.encodeBase64String(bytes);

        model.addAttribute("imageBase64", imageBase64);
        model.addAttribute("username", username);

        return "qrcode";
    }

    @PostMapping("/validate/key")
    public Validation validateKey(@RequestBody ValidationCodeDto body) {
        return new Validation(googleAuthenticator.authorizeUser(body.getUsername(), body.getCode()));
    }

    @GetMapping("/scratches/{username}")
    public List<Integer> getScratches(@PathVariable String username) {
        return getScratchCodes(username);
    }

    private List<Integer> getScratchCodes(@PathVariable String username) {
        return credentialRepository.getUser(username).getScratchCodes();
    }

    @PostMapping("/scratches/")
    public Validation validateScratch(@RequestBody ValidationCodeDto body) {
        List<Integer> scratchCodes = getScratchCodes(body.getUsername());
        Validation validation = new Validation(scratchCodes.contains(body.getCode()));
        scratchCodes.remove(body.getCode());
        return validation;
    }
}
