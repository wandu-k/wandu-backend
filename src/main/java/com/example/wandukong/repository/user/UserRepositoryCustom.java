package com.example.wandukong.repository.user;

import com.example.wandukong.dto.UserDto;

public interface UserRepositoryCustom {

    UserDto getUserInfo(Long userId, Long followCheckUserId);

    int getShopCount(Long userId);

    int getSoldItemCount(Long userId);

    int getDistinctBoughtItemCount(Long userId);

}
