package com.example.scheduledevelopproject.dto;

import lombok.Getter;

@Getter
public class SchedulePostRequestDto {
    private final String title;
    private final String content;
    private final String username;

    public SchedulePostRequestDto(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}
