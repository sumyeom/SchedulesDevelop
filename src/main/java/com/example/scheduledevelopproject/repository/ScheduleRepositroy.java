package com.example.scheduledevelopproject.repository;


import com.example.scheduledevelopproject.entity.Schedule;
import com.example.scheduledevelopproject.exception.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

import static com.example.scheduledevelopproject.exception.ErrorCode.SCHEDULE_NOT_FOUND;

public interface ScheduleRepositroy extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->
                new CustomException(SCHEDULE_NOT_FOUND));
    }

}
