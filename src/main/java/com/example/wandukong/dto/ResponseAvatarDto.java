package com.example.wandukong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseAvatarDto {

    private Long userId;
    private String head;
    private String eye;
    private String mouse;
    private String cloth;

    @Builder
    public ResponseAvatarDto(Long userId, String head, String eye, String mouse, String cloth) {
        this.userId = userId;
        this.head = head;
        this.eye = eye;
        this.mouse = mouse;
        this.cloth = cloth;
    }
}
