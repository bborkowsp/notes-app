package com.notesapp.notesapp.config;

import com.notesapp.notesapp.repository.UserRepository;
import com.notesapp.notesapp.service.AuthUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private final UserRepository userRepository;
    private final AuthUseCases authUseCases;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        delayLogin();
        try {
            return super.authenticate(authentication);
        } catch (AuthenticationException exception) {
            throw exception;
        }
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        super.additionalAuthenticationChecks(userDetails, authentication);
        validateVerificationCode(authentication);
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        return super.createSuccessAuthentication(principal, authentication, user);
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

    private void validateVerificationCode(Authentication authentication) {
        final var user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        String verificationCode = ((CustomWebAuthenticationDetails) authentication.getDetails()).getVerificationCode();
        try {
            authUseCases.checkVerificationCodesMatch(user.getUsername(), Integer.valueOf(verificationCode));
        } catch (IllegalStateException exception) {
            throw new BadCredentialsException(exception.getMessage());
        }
    }
}
