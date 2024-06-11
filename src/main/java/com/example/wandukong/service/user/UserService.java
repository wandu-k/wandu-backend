package com.example.wandukong.service.user;

import com.example.wandukong.dto.UserDto;

public interface UserService {

    UserDto getUserInfo(Long userId, Long followCheckUserId);

}
