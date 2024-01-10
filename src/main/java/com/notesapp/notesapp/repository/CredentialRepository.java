package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.TotpCredentials;
import com.warrenstrange.googleauth.ICredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CredentialRepository implements ICredentialRepository {

    private final UserRepository userRepository;

    @Override
    public String getSecretKey(String username) {
        return userRepository.findByUsername(username)
                .map(user -> user.getCredentials().getSecretKey())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Override
    public void saveUserCredentials(String username, String secretKey, int verificationCode, List<Integer> scratchCodes) {
        TotpCredentials totpCredentials = createUserTotpCredentials(secretKey, verificationCode, scratchCodes);
        userRepository.findByUsername(username).ifPresent(user -> {
            user.setCredentials(totpCredentials);
            userRepository.save(user);
        });
    }

    private TotpCredentials createUserTotpCredentials(String secretKey, int verificationCode, List<Integer> scratchCodes) {
        return TotpCredentials.builder()
                .secretKey(secretKey)
                .verificationCode(verificationCode)
                .scratchCodes(scratchCodes)
                .build();
    }

}