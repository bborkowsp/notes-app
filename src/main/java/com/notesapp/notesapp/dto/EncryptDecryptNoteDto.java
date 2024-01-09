package com.notesapp.notesapp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EncryptDecryptNoteDto {
    private Long encryptDecryptNoteId;
    @NotBlank(message = "Password cannot be blank")
    @Size(max = 120, message = "Password length cannot exceed 120 characters")
    private String password;


    public EncryptDecryptNoteDto(Long o, String s) {

    }

    public void setEncryptDecryptNoteId(Long encryptDecryptNoteId) {
        this.encryptDecryptNoteId = encryptDecryptNoteId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEncryptDecryptNoteId() {
        return encryptDecryptNoteId;
    }

    public String getPassword() {
        return password;
    }

}
