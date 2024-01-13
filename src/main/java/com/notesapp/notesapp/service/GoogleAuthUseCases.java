package com.notesapp.notesapp.service;

import com.google.zxing.WriterException;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;

import java.io.IOException;

public interface GoogleAuthUseCases {
    boolean validateCode(String username, Integer verificationCode);

    String generateQRCodeImageBase64(GoogleAuthenticatorKey key, String username) throws WriterException, IOException;
}
