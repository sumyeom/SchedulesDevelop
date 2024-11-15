package com.example.scheduledevelopproject.dto;

import com.example.scheduledevelopproject.entity.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleGetResponseDto {
    private final Long id;
    private final String username;
    private final String title;
    private final String content;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public ScheduleGetResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.username = schedule.getUser() != null ? schedule.getUser().getUsername() : "";
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.commentCount = schedule.getComments() != null ? schedule.getComments().size() : 0;
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }

    public static ScheduleGetResponseDto toDto(Schedule schedule){
        return new ScheduleGetResponseDto(schedule);
    }
}
