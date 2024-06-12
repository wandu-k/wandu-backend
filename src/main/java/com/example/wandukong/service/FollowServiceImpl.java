package com.example.wandukong.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Friend;
import com.example.wandukong.domain.FriendPK;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.repository.FriendRepository;
import com.example.wandukong.repository.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public List<FriendDto> getFollowingList(Long userId) {
        List<Friend> friends = friendRepository.findAllByFriendId_UserDo_UserId(userId);
        List<FriendDto> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            FriendDto friendDto = FriendDto.builder()
                    .userId(friend.getFriendId().getUserDo().getUserId())
                    .friendId(friend.getFriendId().getFriendDo().getUserId())
                    .nickname(friend.getFriendId().getFriendDo().getNickname())
                    .intro(friend.getFriendId().getFriendDo().getIntro())
                    .profileImage(friend.getFriendId().getFriendDo().getProfileImage())
                    .build();
            friendList.add(friendDto);
        }
        return friendList;
    }

    @Override
    public List<FriendDto> getFollowerList(Long userId) {
        List<Friend> friends = friendRepository.findAllByFriendId_FriendDo_UserId(userId);
        List<FriendDto> friendList = new ArrayList<>();
        for (Friend friend : friends) {
            FriendDto friendDto = FriendDto.builder()
                    .userId(friend.getFriendId().getFriendDo().getUserId())
                    .friendId(friend.getFriendId().getUserDo().getUserId())
                    .nickname(friend.getFriendId().getUserDo().getNickname())
                    .intro(friend.getFriendId().getUserDo().getIntro())
                    .profileImage(friend.getFriendId().getUserDo().getProfileImage())
                    .build();
            friendList.add(friendDto);
        }
        return friendList;
    }

    @Override
    public void following(Long accountId, Long userId) {
        UserDo userDo = userRepository.getReferenceById(accountId);
        UserDo friendDo = userRepository.getReferenceById(userId);
        FriendPK friendPK = new FriendPK(userDo, friendDo);
        if (!friendRepository.existsById(friendPK)) {
            Friend friend = new Friend(friendPK);
            friendRepository.save(friend);
        }
    }

    @Override
    public void unFollowing(Long accountId, Long userId) {
        UserDo userDo = userRepository.getReferenceById(accountId);
        UserDo friendDo = userRepository.getReferenceById(userId);
        FriendPK friendPK = new FriendPK(userDo, friendDo);
        Friend friend = friendRepository.getReferenceById(friendPK);
        friendRepository.delete(friend);
    }

}
