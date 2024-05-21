package com.example.wandukong.repository;

import java.util.Optional;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.UserDto;

public interface AccountRepositoryCustom {

    UserDto getUserInfo(Long userId);

}
