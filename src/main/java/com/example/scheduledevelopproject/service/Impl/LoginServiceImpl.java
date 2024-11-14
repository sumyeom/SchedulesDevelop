package com.example.scheduledevelopproject.service.Impl;

import com.example.scheduledevelopproject.config.PasswordEncoder;
import com.example.scheduledevelopproject.dto.LoginResponseDto;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.exception.CustomException;
import com.example.scheduledevelopproject.repository.UserRepository;
import com.example.scheduledevelopproject.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.scheduledevelopproject.exception.ErrorCode.INVALID_PASSWORD;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDto login(String email, String password) {
        User user = userRepository.findUserByEmailOrElseThrow(email);

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(INVALID_PASSWORD);
        }
        return new LoginResponseDto(user.getId());
    }

}
