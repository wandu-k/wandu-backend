package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.repository.search.ForumPostSearch;

public interface ForumPostRepository extends JpaRepository<ForumPost, Long>, ForumPostSearch{

}
