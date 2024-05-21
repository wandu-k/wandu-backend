package com.example.wandukong.domain;

import com.example.wandukong.dto.FriendDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendId", referencedColumnName = "userId")
    private UserDo friendDo;

    @Builder
    public Friend(Long id, UserDo userDo, UserDo friendDo) {
        this.id = id;
        this.userDo = userDo;
        this.friendDo = friendDo;
    }

    public FriendDto toDto(Friend friend) {

        FriendDto friendDto = FriendDto.builder()
                .id(id)
                .userId(userDo.getUserId())
                .friendId(friendDo.getUserId())
                .build();

        return friendDto;

    }
}