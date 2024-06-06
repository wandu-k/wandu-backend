package com.example.wandukong.repository.user;

import com.example.wandukong.dto.MyStatisticsDto;
import com.example.wandukong.dto.UserDto;

public interface UserRepositoryCustom {

    UserDto getUserInfo(Long userId);

    MyStatisticsDto getMyStatistics(Long userId);

}
