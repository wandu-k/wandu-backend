package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.FriendDto;

public interface FollowService {

    List<FriendDto> getFollowingList(Long userId);

    void following(FriendDto friendDto);

    void unFollowing(FriendDto friendDto);

    List<FriendDto> getFollowerList(Long userId);

}
