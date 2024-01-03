package com.notesapp.notesapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record LoginUserDto(
        @NotBlank @Size(max = 30) String username,
        @NotBlank @Size(max = 120) String password,
        @NotBlank @Size(max = 6) Integer verificationCode
) {
}
