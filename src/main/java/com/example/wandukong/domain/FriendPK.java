package com.example.wandukong.domain;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FriendPK implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private UserDo userDo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friendId", referencedColumnName = "userId")
    private UserDo friendDo;

    public FriendPK(UserDo userDo, UserDo friendDo) {
        this.userDo = userDo;
        this.friendDo = friendDo;
    }

}