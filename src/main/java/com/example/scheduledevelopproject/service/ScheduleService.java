package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;

import java.util.List;

public interface ScheduleService {
    SchedulePostResponseDto save(String title, String content, String username);

    List<ScheduleGetResponseDto> findAll();

    ScheduleGetResponseDto findById(Long id);

    SchedulePostResponseDto updateSchedule(Long id, String title, String content, String username);

    void delete(Long scheduleId);
}
