package com.example.wandukong.service;

import com.example.wandukong.dto.AvatarDto;

public interface AvatarService {

    void putAvatar(Long userId, AvatarDto avatarDto);

    AvatarDto getAvatar(Long userId);

}
