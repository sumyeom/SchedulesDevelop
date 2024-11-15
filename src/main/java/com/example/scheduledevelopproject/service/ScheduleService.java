package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;
import org.springframework.data.domain.Page;

public interface ScheduleService {
    SchedulePostResponseDto createSchedule(String title, String content, String username, Long userId);

    Page<ScheduleGetResponseDto> findAllSchedule(int page, int size);

    ScheduleGetResponseDto findById(Long id);

    SchedulePostResponseDto updateSchedule(Long id, String title, String content);

    void delete(Long scheduleId);
}
