package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostRequestDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePutRequestDto;
import com.example.scheduledevelopproject.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성 API
     * @param {@link SchedulePostRequestDto} 일정 생성 요청 객체
     * @return {@link ResponseEntity<SchedulePostResponseDto>} JSON 응답
     */
    @PostMapping
    public ResponseEntity<SchedulePostResponseDto> createSchedule(
            @RequestBody SchedulePostRequestDto requestDto
    ){
        SchedulePostResponseDto schedulePostResponseDto = scheduleService.createSchedule(requestDto.getTitle(),requestDto.getContent(),requestDto.getUsername());

        return new ResponseEntity<>(schedulePostResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleGetResponseDto>> findAllSchedule(){
        List<ScheduleGetResponseDto> scheduleGetResponseDtoList = scheduleService.findAllSchedule();

        return new ResponseEntity<>(scheduleGetResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetResponseDto> findByIdSchedule(@PathVariable Long scheduleId){
        ScheduleGetResponseDto scheduleGetResponseDto = scheduleService.findById(scheduleId);

        return new ResponseEntity<>(scheduleGetResponseDto,HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}")
    public ResponseEntity<SchedulePostResponseDto> updateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody SchedulePutRequestDto requestDto
    ){
        return new ResponseEntity<>(scheduleService.updateSchedule(scheduleId, requestDto.getTitle(), requestDto.getContent()),HttpStatus.OK);
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long scheduleId){
        scheduleService.delete(scheduleId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
