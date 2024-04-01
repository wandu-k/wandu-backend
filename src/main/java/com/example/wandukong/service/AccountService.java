package com.example.wandukong.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.UserAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;

public interface AccountService {

    void register(UserDto userDto) throws UserAlreadyExistsException;

    void deleteAccount(Long userID);

    void updateProfile(MultipartFile profileImage, UserDto userDto) throws IOException, UserNotFoundException;

    UserDto getMyInfo(String username);

    UserDto getUserInfo(Long userID) throws UserNotFoundException;

    // void login(UserDto userDto, HttpServletRequest request);
}
