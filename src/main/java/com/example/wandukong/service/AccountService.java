package com.example.wandukong.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.UserDto;

public interface AccountService {

    int register(UserDto userDto);

    void deleteAccount(Long userID);

    void updateProfile(MultipartFile profileImage, UserDto userDto) throws IOException;

    UserDto getMyInfo(String username);

    // void login(UserDto userDto, HttpServletRequest request);
}
