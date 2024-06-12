package com.example.wandukong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FriendDto {

    private Long userId;
    private Long friendId;
    private String nickname;
    private String profileImage;
    private String intro;

}
