package com.notesapp.notesapp.service.internal;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.notesapp.notesapp.service.GoogleAuthUseCases;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class GoogleAuthService implements GoogleAuthUseCases {

    private final GoogleAuthenticator googleAuthenticator;

    @Override
    public boolean validateCode(String username, Integer verificationCode) {
        return googleAuthenticator.authorizeUser(username, verificationCode);
    }

    @Override
    public String generateQRCodeImageBase64(GoogleAuthenticatorKey key, String username) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthTotpURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("notes-app", username, key);

        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthTotpURL, BarcodeFormat.QR_CODE, 200, 200);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);

        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeBase64String(bytes);
    }
}
