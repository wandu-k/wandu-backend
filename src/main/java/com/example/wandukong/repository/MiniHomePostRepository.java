package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.MiniHome.MiniHomePost;

public interface MiniHomePostRepository extends JpaRepository<MiniHomePost, Long> {

}
