package com.example.wandukong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findAllByUserDoUserId(Long userId);

    List<Friend> findAllByFriendDoUserId(Long userId);

}
