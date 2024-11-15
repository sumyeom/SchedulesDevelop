package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.dto.ScheduleGetResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePostRequestDto;
import com.example.scheduledevelopproject.dto.SchedulePostResponseDto;
import com.example.scheduledevelopproject.dto.SchedulePutRequestDto;
import com.example.scheduledevelopproject.service.ScheduleService;
import com.example.scheduledevelopproject.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/schedules")
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
            @Valid @RequestBody SchedulePostRequestDto requestDto,
            @SessionAttribute(name = "userId", required = false) Long id,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }
        SchedulePostResponseDto schedulePostResponseDto = scheduleService.createSchedule(requestDto.getTitle(),requestDto.getContent(),requestDto.getUsername(), id);

        return new ResponseEntity<>(schedulePostResponseDto, HttpStatus.OK);
    }

    /**
     * 일정 전체 조회 API
     * @return (@link ResponseEntity<List<ScheduleGetResponseDto>>} JSON 응답
     */
    @GetMapping
    public ResponseEntity<List<ScheduleGetResponseDto>> findAllSchedule(
            @RequestParam(defaultValue = "0",required = false) int page,
            @RequestParam(defaultValue = "10",required = false) int size
    ){

        Page<ScheduleGetResponseDto> allSchedule = scheduleService.findAllSchedule(page, size);

        return new ResponseEntity<>(allSchedule.getContent(),HttpStatus.OK);
    }

    /**
     * 일정 단건 조회 API
     * @param scheduleId    식별자
     * @return {@link ResponseEntity<ScheduleGetResponseDto>} JSON 응답
     */
    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleGetResponseDto> findByIdSchedule(@PathVariable Long scheduleId){
        ScheduleGetResponseDto scheduleGetResponseDto = scheduleService.findById(scheduleId);

        return new ResponseEntity<>(scheduleGetResponseDto,HttpStatus.OK);
    }

    /**
     * 일정 전체 수정 API
     * @param schedulesId 식별자
     * @param {@link requestDto} 일정 수정 요청 객체
     * @return {@link ResponseEntity<SchedulePostResponseDto>} JSON 응답
     * @exception ResponseStatusException 요청 필수값이 없는 경우 400 BAD Request, 식별자로 조회된 Memo가 없는 경우 404 Not Found
     */
    @PutMapping("/{schedulesId}")
    public ResponseEntity<SchedulePostResponseDto> updateSchedule(
            @PathVariable Long schedulesId,
            @Valid @RequestBody SchedulePutRequestDto requestDto,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }
        return new ResponseEntity<>(scheduleService.updateSchedule(schedulesId, requestDto.getTitle(), requestDto.getContent()),HttpStatus.OK);
    }

    /**
     * 일정 삭제 API
     * @param schedulesId 식별자
     * @return {@link ResponseEntity<Void>} 성공시 Data 없이 200OK 상태코드만 응답.
     * @exception ResponseStatusException 식별자로 조회된 일정이 없는 경우 404 Not Found
     */
    @DeleteMapping("/{schedulesId}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long schedulesId){
        scheduleService.delete(schedulesId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
