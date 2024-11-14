package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.UserGetResponseDto;
import com.example.scheduledevelopproject.dto.UserPostResponseDto;

import java.util.List;

public interface UserService {
    UserPostResponseDto createUser(String username, String email);

    List<UserGetResponseDto> findAllUser();

    UserGetResponseDto findByIdUser(Long id);

    void deleteUser(Long userId);
}
