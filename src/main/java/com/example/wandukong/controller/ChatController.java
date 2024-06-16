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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user/minihome")
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

    @GetMapping
    @RequestMapping("/{hpId}/chat")
    public ResponseEntity<?> chatList(@PathVariable Long hpId) {
        List<ChatMessageDto> chatList = chatService.chatList(hpId);
        return new ResponseEntity<>(chatList, HttpStatus.OK);
    }

}
