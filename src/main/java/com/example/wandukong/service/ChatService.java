package com.example.wandukong.service;

import com.example.wandukong.dto.ChatMessageDto;

import java.util.List;

public interface ChatService {
    ChatMessageDto createChat(Long hpId, ChatMessageDto chatMessageDto);

    List<ChatMessageDto> chatList(Long hpId);
}
