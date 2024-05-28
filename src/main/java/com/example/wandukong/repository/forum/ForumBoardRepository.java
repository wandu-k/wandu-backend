package com.example.wandukong.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.forum.ForumBoard;

public interface ForumBoardRepository extends JpaRepository<ForumBoard, Long>{

}
