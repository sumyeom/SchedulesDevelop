package com.example.scheduledevelopproject.dto;

import lombok.Getter;

@Getter
public class UserPostRequestDto {
    private String username;
    private String email;

    public UserPostRequestDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
