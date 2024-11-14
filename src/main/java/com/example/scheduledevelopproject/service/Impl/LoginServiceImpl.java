package com.example.scheduledevelopproject.service.Impl;

import com.example.scheduledevelopproject.dto.LoginResponseDto;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.repository.UserRepository;
import com.example.scheduledevelopproject.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;

    @Override
    public LoginResponseDto login(String email, String password) {
        User user = userRepository.findUserByEmailAndPasswordOrElseThrow(email, password);
        return new LoginResponseDto(user.getId());
    }

}
