package com.notesapp.notesapp.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.notesapp.notesapp.model.User;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CustomAuthenticationProvider extends DaoAuthenticationProvider {

    private static final int MAX_LOGIN_ATTEMPTS = 10;

    private final UserRepository userRepository;
    private final AuthUseCases authUseCases;

    private final Cache<String, Integer> failedLoginAttemptsCache;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("authenticate function called");
        delayLogin();
        checkIfReachedMaxFailedLoginAttemptsCount();

        try {
            return super.authenticate(authentication);
        } catch (AuthenticationException exception) {
            incrementFailedLoginAttemptsCount();
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
        clearFailedLoginAttempts();
        return super.createSuccessAuthentication(principal, authentication, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void delayLogin() {
        try {
            int randomDuration = new Random().nextInt(1000) + 300;
            Thread.sleep(randomDuration);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void validateVerificationCode(Authentication authentication) {
        User user = getUserFromAuthentication(authentication);
        String verificationCode = ((CustomWebAuthenticationDetails) authentication.getDetails()).getVerificationCode();
        verifyVerificationCodeIsANumber(verificationCode);
        try {
            authUseCases.checkVerificationCodesMatch(user.getUsername(), Integer.valueOf(verificationCode));
        } catch (IllegalStateException exception) {
            throw new BadCredentialsException(exception.getMessage());
        }
    }

    private void verifyVerificationCodeIsANumber(String verificationCode) {
        try {
            Integer.valueOf(verificationCode);
        } catch (NumberFormatException exception) {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    private User getUserFromAuthentication(Authentication authentication) {
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }

    private void incrementFailedLoginAttemptsCount() {
        final var request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final var ipAddress = request.getRemoteAddr();
        System.out.println("incrementFailedLoginAttemptsCount " + getFailedLoginAttemptsCount());
        final var failedLoginAttemptsCount = getFailedLoginAttemptsCount() + 1;
        failedLoginAttemptsCache.put(ipAddress, failedLoginAttemptsCount);
    }

    private void checkIfReachedMaxFailedLoginAttemptsCount() {
        final var unsuccessfulLoginAttempts = getFailedLoginAttemptsCount();
        System.out.println("checkIfReachedMaxFailedLoginAttemptsCount unsuccessfulLoginAttempts: " + unsuccessfulLoginAttempts);
        if (unsuccessfulLoginAttempts >= MAX_LOGIN_ATTEMPTS) {
            throw new BadCredentialsException("You have reached the maximum number of unsuccessful login attempts. Please try again later.");
        }
    }

    private int getFailedLoginAttemptsCount() {
        final var request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final var ipAddress = request.getRemoteAddr();
        return failedLoginAttemptsCache.get(ipAddress, key -> 0);
    }

    private void clearFailedLoginAttempts() {
        final var request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        final var ipAddress = request.getRemoteAddr();
        failedLoginAttemptsCache.invalidate(ipAddress);
    }


}
