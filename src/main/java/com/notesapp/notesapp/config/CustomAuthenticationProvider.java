package com.notesapp.notesapp.config;

import com.notesapp.notesapp.service.GoogleAuthUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final GoogleAuthUseCases googleAuthUseCases;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final var username = authentication.getName();
        final var password = authentication.getCredentials().toString();
        //    final var verificationCode = authentication.getDetails().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        checkUserExists(userDetails);
        checkPasswordsMatch(password, userDetails.getPassword());
        //     checkVerificationCodesMatch(username, Integer.parseInt(verificationCode));

        return new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    private void checkUserExists(UserDetails userDetails) {
        if (userDetails == null) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private void checkPasswordsMatch(String password, String encodedPassword) {
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new IllegalStateException("Password does not match");
        }
    }

    private void checkVerificationCodesMatch(String username, Integer verificationCode) {
        System.out.println("checkVerificationCodesMatch, verificationCode: " + verificationCode);
        if (!googleAuthUseCases.validateCode(username, verificationCode)) {
            throw new IllegalStateException("Verification code does not match");
        }
        System.out.println("not throw IllegalStateException");
    }
}
