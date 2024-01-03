package com.notesapp.notesapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record RegisterUserDto(
        @NotBlank @Size(max = 30) String username,
        @NotBlank @Size(max = 255) String email,
        @NotBlank @Size(max = 120) String password
) {

}
