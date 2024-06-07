package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.Avatar;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

}
