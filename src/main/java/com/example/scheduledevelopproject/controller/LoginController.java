package com.example.scheduledevelopproject.controller;

import com.example.scheduledevelopproject.config.PasswordEncoder;
import com.example.scheduledevelopproject.dto.LoginRequestDto;
import com.example.scheduledevelopproject.dto.LoginResponseDto;
import com.example.scheduledevelopproject.dto.UserPostRequestDto;
import com.example.scheduledevelopproject.dto.UserPostResponseDto;
import com.example.scheduledevelopproject.exception.CustomException;
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

import static com.example.scheduledevelopproject.exception.ErrorCode.USER_NOT_FOUND;

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
            throw new CustomException(USER_NOT_FOUND);
        }
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request){
        //로그인 하지않으면 HttpSession이 null로 반환된다.
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate(); // 해당 세션(데이터)을 삭제
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
