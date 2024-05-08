package com.example.wandukong.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.IncorrectPasswordException;
import com.example.wandukong.exception.CustomException.UserAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;

public interface AccountService {

    void register(MultipartFile profileImage, UserDto userDto) throws UserAlreadyExistsException, IOException;

    void deleteAccount(Long userId);

    void updateProfile(MultipartFile profileImage, UserDto userDto) throws UserNotFoundException, IOException;

    UserDto getUserInfo(Long userId) throws UserNotFoundException;

    void updatePassword(Long userId, String currentPassword, String newPassword)
            throws UserNotFoundException, IncorrectPasswordException;

}
