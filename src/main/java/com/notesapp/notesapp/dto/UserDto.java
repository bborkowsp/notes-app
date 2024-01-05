package com.notesapp.notesapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;


@Builder
@Jacksonized
public record UserDto(
        @NotBlank @Size(max = 20) String username,
        @NotBlank @Size(max = 50) String email,
        @NotBlank @Size(max = 120) String password
) {
}
