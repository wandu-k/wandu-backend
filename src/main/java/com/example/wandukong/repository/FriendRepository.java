package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.Friend;

public interface FriendRepository extends JpaRepository<Friend, Long> {

}
