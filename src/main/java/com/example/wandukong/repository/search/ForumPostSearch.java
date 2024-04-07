package com.example.wandukong.repository.search;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.ForumPost;

public interface ForumPostSearch {

  Page<ForumPost> search();
}
