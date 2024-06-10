package com.example.wandukong.dto;

import lombok.Getter;

@Getter
public class FriendDto {

    private Long userId;
    private Long friendId;

    public FriendDto(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

}
