package com.example.wandukong.dto;

import lombok.Getter;

@Getter
public class AvatarDto {

    private String head;
    private String eye;
    private String mouse;
    private String cloth;

    public AvatarDto(String head, String eye, String mouse, String cloth) {
        this.head = head;
        this.eye = eye;
        this.mouse = mouse;
        this.cloth = cloth;
    }

    public AvatarDto() {
    }

}
