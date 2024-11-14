package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.dto.UserGetResponseDto;
import com.example.scheduledevelopproject.dto.UserPostRequestDto;
import com.example.scheduledevelopproject.dto.UserPostResponseDto;
import com.example.scheduledevelopproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserPostResponseDto> createUser(@RequestBody UserPostRequestDto requestDto){
        UserPostResponseDto userPostResponseDto = userService.createUser(requestDto.getUsername(), requestDto.getEmail());
        return new ResponseEntity<>(userPostResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public  ResponseEntity<List<UserGetResponseDto>> findAllUser(){
        List<UserGetResponseDto> userGetResponseDtoList = userService.findAllUser();
        return new ResponseEntity<>(userGetResponseDtoList,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserGetResponseDto> findByIdUser(@PathVariable Long userId){
        UserGetResponseDto userGetResponseDto = userService.findByIdUser(userId);
        return new ResponseEntity<>(userGetResponseDto,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId){
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
