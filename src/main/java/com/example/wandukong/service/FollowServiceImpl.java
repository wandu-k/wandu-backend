package com.example.wandukong.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Friend;
import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.repository.FriendRepository;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    FriendRepository friendRepository;

    @Override
    public List<FriendDto> getFollowingList(Long userId) {
        List<Friend> friends = friendRepository.findAllByUserId(userId);
        List<FriendDto> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            FriendDto friendDto = friend.toDto(friend);
            friendList.add(friendDto);
        }
        return friendList;
    }

    @Override
    public List<FriendDto> getFollowerList(Long userId) {
        List<Friend> friends = friendRepository.findAllByFriendId(userId);
        List<FriendDto> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            FriendDto friendDto = friend.toDto(friend);
            friendList.add(friendDto);
        }
        return friendList;
    }

    @Override
    public void following(FriendDto friendDto) {

        Friend friend = friendDto.toEntity(friendDto);

        friendRepository.save(friend);
    }

    @Override
    public void unFollowing(FriendDto friendDto) {
        Friend friend = friendDto.toEntity(friendDto);

        friendRepository.delete(friend);
    }

}
