package com.example.wandukong.service;

import com.example.wandukong.dto.UserDto;

public interface AccountService {

    int register(UserDto userDto);

    // void login(UserDto userDto, HttpServletRequest request);
}
