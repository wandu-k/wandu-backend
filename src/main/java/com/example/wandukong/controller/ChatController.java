package com.example.wandukong.controller;

import com.example.wandukong.dto.ChatMessageDto;
import com.example.wandukong.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{hpId}")
    @SendTo("/room/{hpId}")
    public ChatMessageDto chat(@DestinationVariable Long hpId, ChatMessageDto chatMessageDto) {
        log.info("유저 아이디 : " + chatMessageDto.getUserId());
        log.info("메세지 : " + chatMessageDto.getMessage());
        //채팅 저장
        ChatMessageDto dto = chatService.createChat(hpId, chatMessageDto);
        return dto;
    }
}
