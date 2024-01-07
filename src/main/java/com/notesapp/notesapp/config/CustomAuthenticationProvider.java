package com.notesapp.notesapp.config;

import com.notesapp.notesapp.model.User;
import com.notesapp.notesapp.repository.UserRepository;
import com.notesapp.notesapp.service.AuthUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserRepository userRepository;
    private final AuthUseCases authUseCases;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        delayLogin();
        Optional<User> user = userRepository.findByUsername(authentication.getName());
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid credentials");
        }
        String verificationCode = ((CustomWebAuthenticationDetails) authentication.getDetails()).getVerificationCode();
        try {
            authUseCases.checkVerificationCodesMatch(user.get().getUsername(), Integer.valueOf(verificationCode));
        } catch (IllegalStateException exception) {
            throw new BadCredentialsException(exception.getMessage());
        }
        final Authentication result = super.authenticate(authentication);
        return new UsernamePasswordAuthenticationToken(user, result.getCredentials(), result.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void delayLogin() {
        Random random = new Random();
        try {
            int randomDuration = random.nextInt(1000) + 300;
            Thread.sleep(randomDuration);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }
}
