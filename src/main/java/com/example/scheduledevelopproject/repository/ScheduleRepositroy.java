package com.example.scheduledevelopproject.repository;


import com.example.scheduledevelopproject.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface ScheduleRepositroy extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrElseThrow(Long id){
        return findById(id)
                .orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Does not exist id = " + id));
    }

    @Modifying
    @Query("DELETE FROM Schedule s WHERE s.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
