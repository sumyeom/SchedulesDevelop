package com.example.scheduledevelopproject.dto;

import lombok.Getter;

@Getter
public class SchedulePutRequestDto {
    private final String title;
    private final String content;

    public SchedulePutRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
