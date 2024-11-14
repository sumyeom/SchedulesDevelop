package com.example.scheduledevelopproject.dto;

import lombok.Getter;

@Getter
public class UserPostRequestDto {
    private String username;
    private String email;
    private String password;

    public UserPostRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
