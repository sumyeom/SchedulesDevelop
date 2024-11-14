package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.dto.*;
import com.example.scheduledevelopproject.service.LoginService;
import com.example.scheduledevelopproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto reqeustDto,
            HttpServletRequest request
    ){
        LoginResponseDto responseDto = loginService.login(reqeustDto.getEmail(), reqeustDto.getPassword());
        Long userId = responseDto.getId();
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        HttpSession session = request.getSession();
        session.setAttribute("email", reqeustDto.getEmail());
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        //로그인 하지않으면 HttpSession이 null로 반환된다.
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate(); // 해당 세션(데이터)dmf tkrwp
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserPostResponseDto> signUp(
            @RequestBody UserPostRequestDto requestDto
    ){
        UserPostResponseDto userPostResponseDto = userService.createUser(requestDto.getUsername(), requestDto.getEmail(),requestDto.getPassword());
        return new ResponseEntity<>(userPostResponseDto, HttpStatus.OK);
    }
}
