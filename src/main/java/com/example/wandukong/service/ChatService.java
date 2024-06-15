package com.example.wandukong.service;

import com.example.wandukong.dto.ChatMessageDto;

public interface ChatService {
    void createChat(Long hpId, ChatMessageDto chatMessageDto);
}
