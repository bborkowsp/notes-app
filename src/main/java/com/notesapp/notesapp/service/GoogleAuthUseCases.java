package com.notesapp.notesapp.service;

public interface GoogleAuthUseCases {
    boolean validateCode(String username, Integer verificationCode);
}
