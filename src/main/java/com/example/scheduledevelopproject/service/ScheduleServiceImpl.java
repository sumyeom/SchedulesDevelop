package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;
import com.example.scheduledevelopproject.entity.Schedule;
import com.example.scheduledevelopproject.repository.ScheduleRepositroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepositroy scheduleRepositroy;

    @Override
    public SchedulePostResponseDto save(String title, String content, String username) {
        Schedule schedule = new Schedule(title, content, username);
        scheduleRepositroy.save(schedule);
        return new SchedulePostResponseDto(schedule.getId());
    }

    @Override
    public List<ScheduleGetResponseDto> findAll() {
        return scheduleRepositroy.findAll()
                .stream()
                .map(ScheduleGetResponseDto::toDto)
                .toList();
    }

    @Override
    public ScheduleGetResponseDto findById(Long id) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(id);
        return new ScheduleGetResponseDto(schedule.getId(), schedule.getTitle(), schedule.getContent(), schedule.getUsername());
    }

    @Transactional
    @Override
    public SchedulePostResponseDto updateSchedule(Long id, String title, String content, String username) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(id);
        schedule.setTitle(title);
        schedule.setContent(content);
        schedule.setUsername(username);

        scheduleRepositroy.save(schedule);
        return new SchedulePostResponseDto(schedule.getId());
    }

    @Override
    public void delete(Long scheduleId) {
        Schedule findSchedule = scheduleRepositroy.findByIdOrElseThrow(scheduleId);
        scheduleRepositroy.delete(findSchedule);
    }
}