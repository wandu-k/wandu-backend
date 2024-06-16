package com.example.wandukong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChatMessageDto {
    private Long userId;
    private String nickname;
    private String profileImage;
    private String message;
}
