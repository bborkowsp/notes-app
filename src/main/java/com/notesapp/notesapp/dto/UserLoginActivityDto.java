package com.notesapp.notesapp.dto;

import java.time.LocalDateTime;

public record UserLoginActivityDto(
        String loginDate,
        String ipAddress,
        String userAgent,
        boolean loginResult
) {
}
