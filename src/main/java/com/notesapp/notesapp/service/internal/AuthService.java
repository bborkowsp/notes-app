package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.RegisterUserDto;
import com.notesapp.notesapp.model.Role;
import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.UserRepository;
import com.notesapp.notesapp.service.AuthUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class AuthService implements AuthUseCases, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterUserDto registerUserDto) {
        checkIfUserAlreadyExists(registerUserDto.username());
        checkIfEmailAlreadyExists(registerUserDto.email());
        final var encodedPassword = passwordEncoder.encode(registerUserDto.password());
        final var user = new User(registerUserDto.username(), registerUserDto.email(), encodedPassword, Role.USER);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));
    }


    private void checkIfUserAlreadyExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalStateException("User already exists");
        }
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalStateException("Email already exists");
        }
    }
}
