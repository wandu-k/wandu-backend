package com.example.wandukong.service.user;

import org.springframework.stereotype.Service;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceimpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto getUserInfo(Long userId, Long followCheckUserId) {
        return userRepository.getUserInfo(userId, followCheckUserId);
    }

}
