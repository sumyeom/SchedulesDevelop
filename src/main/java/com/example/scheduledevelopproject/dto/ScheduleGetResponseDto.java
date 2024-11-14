package com.example.scheduledevelopproject.dto;

import com.example.scheduledevelopproject.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleGetResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String username;

    public ScheduleGetResponseDto(Long id, String title, String content, String username) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public static ScheduleGetResponseDto toDto(Schedule schedule){
        return new ScheduleGetResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getUsername());
    }
}
