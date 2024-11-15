package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.config.PasswordEncoder;
import com.example.scheduledevelopproject.dto.UserGetResponseDto;
import com.example.scheduledevelopproject.dto.UserPostRequestDto;
import com.example.scheduledevelopproject.dto.UserPostResponseDto;
import com.example.scheduledevelopproject.service.UserService;
import com.example.scheduledevelopproject.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 유저 등록 API
     * @param {@link UserPostRequestDto} 유저 생성 요청 객체
     * @param bindingResult error 모아주는 객체
     * @return {@link ResponseEntity< UserPostResponseDto >} JSON 응답
     */
    @PostMapping
    public ResponseEntity<UserPostResponseDto> createUser(
            @Valid @RequestBody UserPostRequestDto requestDto,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }
        UserPostResponseDto userPostResponseDto = userService.createUser(requestDto.getUsername(), requestDto.getEmail(),passwordEncoder.encode(requestDto.getPassword()));

        return new ResponseEntity<>(userPostResponseDto, HttpStatus.OK);
    }

    /**
     * 전체 유저 조회
     * @return {@link ResponseEntity<List<UserGetResponseDto>>} JSON LIST 응답
     */
    @GetMapping
    public  ResponseEntity<List<UserGetResponseDto>> findAllUser(){
        List<UserGetResponseDto> userGetResponseDtoList = userService.findAllUser();
        return new ResponseEntity<>(userGetResponseDtoList,HttpStatus.OK);
    }

    /**
     * 선택 유저 조회
     * @param userId 조회할 userId
     * @return {@link ResponseEntity<UserGetResponseDto>} JSON 응답
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserGetResponseDto> findByIdUser(@PathVariable Long userId){
        UserGetResponseDto userGetResponseDto = userService.findByIdUser(userId);
        return new ResponseEntity<>(userGetResponseDto,HttpStatus.OK);
    }

    /**
     * 선택 유저 삭제
     * @param userId 삭제할 userid
     * @return HttpStatus 반환
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
