package com.example.wandukong.service;

import com.example.wandukong.dto.RequestAvatarDto;
import com.example.wandukong.dto.ResponseAvatarDto;

public interface AvatarService {
    ResponseAvatarDto getAvatar(Long userId);

    void putAvatar(Long userId, Long itemId, RequestAvatarDto requestAvatarDto);

}
