package com.example.scheduledevelopproject.dto;

import com.example.scheduledevelopproject.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGetResponseDto {
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserGetResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }

    public static UserGetResponseDto toDto(User user){
        return new UserGetResponseDto(user);
    }
}
