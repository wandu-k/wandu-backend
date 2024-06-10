package com.example.wandukong.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "Friend")
public class Friend {

    @EmbeddedId
    @Column(name = "friendId", unique = true)
    private FriendPK friendId;

    public Friend(FriendPK friendId) {
        this.friendId = friendId;
    }
}