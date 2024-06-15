package com.example.wandukong.controller;

import com.example.wandukong.dto.ChatMessageDto;
import com.example.wandukong.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/{hpId}")
    @SendTo("/room/{hpId}")
    public ResponseEntity<?> chat(@DestinationVariable Long hpId, ChatMessageDto chatMessageDto)
    {
        //채팅 저장
        chatService.createChat(hpId, chatMessageDto);
        return new ResponseEntity("전송 완료", HttpStatus.OK);
    }
}
