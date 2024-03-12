package com.example.demo.service;

import com.example.demo.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AccountService {

    int register(UserDto userDto);

    void login(UserDto userDto, HttpServletRequest request);
}
