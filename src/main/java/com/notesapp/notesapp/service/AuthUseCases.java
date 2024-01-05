package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.RegisterUserDto;


public interface AuthUseCases {
    void register(RegisterUserDto registerUserDto);

    void checkVerificationCodesMatch(String username, Integer verificationCode);

}
