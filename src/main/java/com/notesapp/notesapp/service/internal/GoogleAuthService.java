package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.service.GoogleAuthUseCases;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleAuthService implements GoogleAuthUseCases {

    private final GoogleAuthenticator googleAuthenticator;

    @Override
    public boolean validateCode(String username, Integer verificationCode) {
        return googleAuthenticator.authorizeUser(username, verificationCode);
    }
}
