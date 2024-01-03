package com.notesapp.notesapp.repository;

import com.notesapp.notesapp.model.UserTOTP;
import com.notesapp.notesapp.model.UserTOTPCredentials;
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
        UserTOTPCredentials userTOTPCredentials = createUserTOTPCredentials(secretKey, verificationCode, scratchCodes);

        userRepository.findByUsername(username).ifPresent(user -> {
            user.setCredentials(userTOTPCredentials);
            userRepository.save(user);
        });
    }

    private UserTOTPCredentials createUserTOTPCredentials(String secretKey, int verificationCode, List<Integer> scratchCodes) {
        return UserTOTPCredentials.builder()
                .secretKey(secretKey)
                .verificationCode(verificationCode)
                .scratchCodes(scratchCodes)
                .build();
    }
//    private final Map<String, UserTOTP> usersKeys = new HashMap<String, UserTOTP>() {{
//        put("kek@kek.com", null);
//        put("alice@gmail.com", null);
//    }};


    public UserTOTP getUser(String username) {
        //   return usersKeys.get(username);
        throw new IllegalArgumentException("Not implemented");
    }
}