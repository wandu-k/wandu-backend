package com.example.wandukong.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.IncorrectPasswordException;
import com.example.wandukong.exception.CustomException.UserAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.security.jwt.JwtToken;

public interface AccountService {

    void register(MultipartFile profileImage, UserDto userDto) throws UserAlreadyExistsException, IOException;

    void deleteAccount(Long userId);

    void updateProfile(MultipartFile profileImage, UserDto userDto) throws UserNotFoundException, IOException;

    UserDto getMyInfo(String username);

    UserDto getUserInfo(Long userId) throws UserNotFoundException;

    JwtToken login(String username, String password);

    void updatePassword(Long userId, String currentPassword, String newPassword)
            throws UserNotFoundException, IncorrectPasswordException;

    // void login(UserDto userDto, HttpServletRequest request);
}
