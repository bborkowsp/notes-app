package com.notesapp.notesapp.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Jacksonized
public record UserDto(
        @NotBlank @Size(max = 20) String username,
        @NotBlank @Size(max = 50) String email,
        @NotBlank @Size(max = 120) String password
) {
}
