package com.example.wandukong.service;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.ForumPostDto;

import jakarta.transaction.Transactional;

@Transactional
public interface ForumPostService {

  ForumPostDto get(Long postID);

  default ForumPostDto entityToDto(ForumPost forumPost) {

    return ForumPostDto.builder()
      .postID(forumPost.getPostID())
      .title(forumPost.getTitle())
      .content(forumPost.getContent())
      .writeDate(forumPost.getWriteDate())
      .state(forumPost.getState())
      .build();
  }

  default ForumPost dtoToEntity(ForumPostDto forumPostDto) {

    return ForumPost.builder()
      .postID(forumPostDto.getPostID())
      .title(forumPostDto.getTitle())
      .content(forumPostDto.getContent())
      .writeDate(forumPostDto.getWriteDate())
      .state(forumPostDto.getState())
      .build();
  }
}