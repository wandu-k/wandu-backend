package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.domain.Friend;
import com.example.wandukong.dto.FriendDto;

public interface FriendService {

    List<FriendDto> getFriendList();

    void addFriend(FriendDto friendDto);

    void deleteFriend(FriendDto friendDto);

}
