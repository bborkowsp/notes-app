package com.notesapp.notesapp.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
    private final String verificationCode;

    public CustomWebAuthenticationDetails(HttpServletRequest httpServletRequest) {
        super(httpServletRequest);
        verificationCode = httpServletRequest.getParameter("verificationCode");
    }

    public String getVerificationCode() {
        return verificationCode;
    }
}
