package com.notesapp.notesapp.service;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.dto.UserLoginActivityDto;
import com.notesapp.notesapp.model.User;

import java.util.List;


public interface AuthUseCases {
    void register(RegisterUserDto registerUserDto);

    void checkVerificationCodesMatch(String username, Integer verificationCode);

    List<UserLoginActivityDto> getUserAccountLoginActivityHistory(User user);
}
