package com.example.scheduledevelopproject.service.Impl;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;
import com.example.scheduledevelopproject.entity.Schedule;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.exception.CustomException;
import com.example.scheduledevelopproject.repository.ScheduleRepositroy;
import com.example.scheduledevelopproject.repository.UserRepository;
import com.example.scheduledevelopproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.scheduledevelopproject.exception.ErrorCode.INVALID_USER_NAME;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepositroy scheduleRepositroy;
    private final UserRepository userRepository;

    @Override
    public SchedulePostResponseDto createSchedule(String title, String content, String username, Long userId) {

        if(!isUsernameMatching(userId,username)){
            throw new CustomException(INVALID_USER_NAME);
        }
        User user = userRepository.findByIdOrElseThrow(userId);

        Schedule schedule = new Schedule(title, content);
        schedule.setUser(user);
        scheduleRepositroy.save(schedule);
        return new SchedulePostResponseDto(schedule.getId());
    }

    @Override
    public Page<ScheduleGetResponseDto> findAllSchedule(int page, int size) {
        if(page > 0){
            page--;
        }
        Pageable pageable = PageRequest.of(page,size, Sort.by(Sort.Order.desc("modifiedAt")));
        Page<Schedule> schedules = scheduleRepositroy.findAll(pageable);

        return schedules.map(ScheduleGetResponseDto::new);
    }

    @Override
    public ScheduleGetResponseDto findById(Long id) {
        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(id);
        User user = schedule.getUser();
        return new ScheduleGetResponseDto(schedule);
    }

    @Transactional
    @Override
    public SchedulePostResponseDto updateSchedule(Long id, String title, String content,Long seesionId) {

        Schedule schedule = scheduleRepositroy.findByIdOrElseThrow(id);
        if(schedule.getUser().getId()!=seesionId){
            throw new CustomException(INVALID_USER_NAME);
        }
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

    private boolean isUsernameMatching(Long id, String username){
        return userRepository.findById(id)
                .map(user -> user.getUsername().equals(username))
                .orElse(false);
    }
}
