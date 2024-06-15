package com.example.wandukong.service;

import com.example.wandukong.dto.ChatMessageDto;

public interface ChatService {
    ChatMessageDto createChat(Long hpId, ChatMessageDto chatMessageDto);
}
