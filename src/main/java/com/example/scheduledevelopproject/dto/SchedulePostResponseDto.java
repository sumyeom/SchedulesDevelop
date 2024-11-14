package com.example.scheduledevelopproject.dto;

import lombok.Getter;

@Getter
public class SchedulePostResponseDto {
    private final Long id;

    public SchedulePostResponseDto(Long id) {
        this.id = id;
    }
}
