package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.LoginResponseDto;

public interface LoginService {
    LoginResponseDto login(String username, String password);
}
