package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.ForumBoard;

public interface ForumBoardRepository extends JpaRepository<ForumBoard, Long>{

}
