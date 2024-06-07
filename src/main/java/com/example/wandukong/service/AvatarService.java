package com.example.wandukong.service;

import com.example.wandukong.dto.AvatarDto;

public interface AvatarService {

    void patchAvatar(Long userId, AvatarDto avatarDto);

    AvatarDto getAvatar(Long userId);

}
