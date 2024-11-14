package com.example.scheduledevelopproject.dto;

import lombok.Getter;

@Getter
public class UserPostResponseDto {
    private final Long id;

    public UserPostResponseDto(Long id) {
        this.id = id;
    }
}
