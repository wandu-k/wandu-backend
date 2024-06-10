package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.FriendDto;

public interface FollowService {

    List<FriendDto> getFollowingList(Long userId);

    List<FriendDto> getFollowerList(Long userId);

    void following(Long accountId, Long userId);

    void unFollowing(Long accountId, Long userId);

}
