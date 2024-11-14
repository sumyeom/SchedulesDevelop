package com.example.scheduledevelopproject.service.Impl;

import com.example.scheduledevelopproject.dto.UserGetResponseDto;
import com.example.scheduledevelopproject.dto.UserPostResponseDto;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.repository.ScheduleRepositroy;
import com.example.scheduledevelopproject.repository.UserRepository;
import com.example.scheduledevelopproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService {

    private final UserRepository userRepository;
    private final ScheduleRepositroy scheduleRepositroy;

    @Override
    public UserPostResponseDto createUser(String username, String email, String password) {
        User user = new User(username,email,password);
        User savedUser = userRepository.save(user);
        return new UserPostResponseDto(savedUser.getId());
    }

    @Override
    public List<UserGetResponseDto> findAllUser() {
        return userRepository.findAll()
                .stream()
                .map(UserGetResponseDto::toDto)
                .toList();
    }

    @Override
    public UserGetResponseDto findByIdUser(Long id) {
        User user = userRepository.findByIdOrElseThrow(id);
        return new UserGetResponseDto(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        scheduleRepositroy.deleteByUserId(userId);
        User user = userRepository.findByIdOrElseThrow(userId);
        userRepository.delete(user);
    }



}
