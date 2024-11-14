package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.config.PasswordEncoder;
import com.example.scheduledevelopproject.dto.*;
import com.example.scheduledevelopproject.service.LoginService;
import com.example.scheduledevelopproject.service.UserService;
import com.example.scheduledevelopproject.util.ValidationUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpServletRequest request,
            BindingResult bindingResult
    ){
        // 유효성 검사
        if(bindingResult.hasErrors()) {
            ValidationUtils.bindErrorMessage(bindingResult);
        }

        LoginResponseDto responseDto = loginService.login(requestDto.getEmail(), requestDto.getPassword());
        Long userId = responseDto.getId();
        if(userId == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        HttpSession session = request.getSession();
        session.setAttribute("email", requestDto.getEmail());
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
        UserPostResponseDto userPostResponseDto = userService.createUser(requestDto.getUsername(), requestDto.getEmail(),passwordEncoder.encode(requestDto.getPassword()));
        return new ResponseEntity<>(userPostResponseDto, HttpStatus.OK);
    }
}
