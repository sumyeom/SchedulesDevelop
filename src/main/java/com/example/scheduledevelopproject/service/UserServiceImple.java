package com.example.scheduledevelopproject.service;

import com.example.scheduledevelopproject.dto.UserGetResponseDto;
import com.example.scheduledevelopproject.dto.UserPostResponseDto;
import com.example.scheduledevelopproject.entity.User;
import com.example.scheduledevelopproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

    private final UserRepository userRepository;

    @Override
    public UserPostResponseDto createUser(String username, String email) {
        User user = new User(username,email);
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

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findByIdOrElseThrow(userId);
        userRepository.delete(user);
    }


}
