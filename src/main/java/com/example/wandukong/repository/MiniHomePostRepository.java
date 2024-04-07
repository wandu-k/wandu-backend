package com.example.wandukong.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.MiniHome.MiniHomePost;

public interface MiniHomePostRepository extends JpaRepository<MiniHomePost, Long> {

    Optional<MiniHomePost> findByPostIDAndUserID(Long postID, Long userID);

}
