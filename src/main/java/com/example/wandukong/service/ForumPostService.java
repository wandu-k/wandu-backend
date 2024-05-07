package com.example.wandukong.service;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;

import jakarta.transaction.Transactional;

@Transactional
public interface ForumPostService {

  ForumPostDto get(Long postID);

  Long register(ForumPostDto forumPostDto);

  void modify(ForumPostDto forumPostDto);

  void remove(Long postID);

  PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto);

  default ForumPostDto entityToDto(ForumPost forumPost) {

    return ForumPostDto.builder()
        .postId(forumPost.getPostId())
        .title(forumPost.getTitle())
        .content(forumPost.getContent())
        .writeDate(forumPost.getWriteDate())
        .state(forumPost.getState())
        .build();
  }

  default ForumPost dtoToEntity(ForumPostDto forumPostDto) {

    return ForumPost.builder()
        .postId(forumPostDto.getPostId())
        .title(forumPostDto.getTitle())
        .content(forumPostDto.getContent())
        .writeDate(forumPostDto.getWriteDate())
        .state(forumPostDto.getState())
        .build();
  }
}
