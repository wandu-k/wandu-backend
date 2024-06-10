package com.example.wandukong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.Friend;
import com.example.wandukong.domain.FriendPK;

public interface FriendRepository extends JpaRepository<Friend, FriendPK> {

    List<Friend> findAllByFriendId_UserDo_UserId(Long userId);

    List<Friend> findAllByFriendId_FriendDo_UserId(Long userId);

}
