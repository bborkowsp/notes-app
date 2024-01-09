package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.UserRepository;
import com.notesapp.notesapp.service.AuthUseCases;
import com.notesapp.notesapp.service.GoogleAuthUseCases;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthService implements AuthUseCases, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthUseCases googleAuthUseCases;

    @Override
    public void register(RegisterUserDto registerUserDto) {
        checkIfPasswordsMatch(registerUserDto.password(), registerUserDto.matchingPassword());
        checkIfUserAlreadyExists(registerUserDto.username(), registerUserDto.email());
        final var encodedPassword = passwordEncoder.encode(registerUserDto.password());
        final var user = new User(registerUserDto.username(), registerUserDto.email(), encodedPassword);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void checkVerificationCodesMatch(String username, Integer verificationCode) {
        if (!googleAuthUseCases.validateCode(username, verificationCode)) {
            throw new IllegalStateException("Invalid login credentials");
        }
    }

    private void checkIfUserAlreadyExists(String username, String email) {
        if (userRepository.existsByUsername(username) || userRepository.existsByEmail(email)) {
            throw new IllegalStateException("User already exists");
        }
    }

    private void checkIfPasswordsMatch(String password, String matchingPassword) {
        if (!password.equals(matchingPassword)) {
            throw new IllegalStateException("Passwords do not match");
        }
    }


}
