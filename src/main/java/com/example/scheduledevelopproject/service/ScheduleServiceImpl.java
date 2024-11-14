package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;
import com.example.scheduledevelopproject.entity.Schedule;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.repository.ScheduleRepositroy;
import com.example.scheduledevelopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepositroy scheduleRepositroy;
    private final UserRepository userRepository;

    @Override
    public SchedulePostResponseDto createSchedule(String title, String content, String username) {
        User user = userRepository.findUserByUsernameOrElseThrow(username);

        Schedule schedule = new Schedule(title, content);
        schedule.setUser(user);
        scheduleRepositroy.save(schedule);
        return new SchedulePostResponseDto(schedule.getId());
    }

    @Override
    public List<ScheduleGetResponseDto> findAllSchedule() {
        return scheduleRepositroy.findAll()
                .stream()
                .map(ScheduleGetResponseDto::toDto)
                .toList();
    }

    @Override
    public ScheduleGetResponseDto findById(Long id) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(id);
        User user = schedule.getUser();
        return new ScheduleGetResponseDto(schedule);
    }

    @Transactional
    @Override
    public SchedulePostResponseDto updateSchedule(Long id, String title, String content) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(id);
        schedule.setTitle(title);
        schedule.setContent(content);

        scheduleRepositroy.save(schedule);
        return new SchedulePostResponseDto(schedule.getId());
    }

    @Override
    public void delete(Long scheduleId) {
        Schedule findSchedule = scheduleRepositroy.findByIdOrElseThrow(scheduleId);
        scheduleRepositroy.delete(findSchedule);
    }
}
