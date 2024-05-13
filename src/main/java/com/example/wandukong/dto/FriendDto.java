package com.example.wandukong.dto;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.wandukong.domain.Friend;
import com.example.wandukong.domain.UserDo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FriendDto {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    private Long id;
    private Long userId;
    private Long friendId;

    @Builder
    public FriendDto(Long id, Long userId, Long friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
    }

    public FriendDto() {
        // TODO Auto-generated constructor stub
    }

    public Friend toEntity(FriendDto friendDto) {

        Friend friend = Friend.builder()
                .id(id)
                .userDo(UserDo.builder().userId(userDetails.getUserDto().getUserId()).build())
                .friendDo(UserDo.builder().userId(friendId).build())
                .build();

        return friend;
    }

}
