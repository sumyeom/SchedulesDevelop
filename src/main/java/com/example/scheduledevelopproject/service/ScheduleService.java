package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;

import java.util.List;

public interface ScheduleService {
    SchedulePostResponseDto createSchedule(String title, String content, String username);

    List<ScheduleGetResponseDto> findAllSchedule();

    ScheduleGetResponseDto findById(Long id);

    SchedulePostResponseDto updateSchedule(Long id, String title, String content);

    void delete(Long scheduleId);
}
