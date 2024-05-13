package com.example.wandukong.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Friend;
import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.repository.FriendRepository;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendRepository friendRepository;

    @Override
    public List<FriendDto> getFriendList() {
        List<Friend> friends = friendRepository.findAll();
        List<FriendDto> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            FriendDto friendDto = friend.toDto(friend);
            friendList.add(friendDto);
        }
        return friendList;
    }

    @Override
    public void addFriend(FriendDto friendDto) {

        Friend friend = friendDto.toEntity(friendDto);

        friendRepository.save(friend);
    }

    @Override
    public void deleteFriend(FriendDto friendDto) {
        Friend friend = friendDto.toEntity(friendDto);

        friendRepository.delete(friend);
    }

}
