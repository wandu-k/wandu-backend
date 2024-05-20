package com.example.wandukong.service;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;

import com.example.wandukong.exception.CustomException;
import com.example.wandukong.model.ApiResponse;
import jakarta.transaction.Transactional;

import static com.example.wandukong.exception.CustomException.*;

public interface ForumPostService {

  ForumPostDto get(Long postId) throws PostNotFoundException;

  ApiResponse modify(ForumPostDto forumPostDto) throws BoardNotFoundException;

  void remove(Long userId, Long postId) throws PostNotFoundException, PermissionDeniedException;

  PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto);

//  default ForumPostDto entityToDto(ForumPost forumPost) {
//
//    return ForumPostDto.builder()
//        .postId(forumPost.getPostId())
//        .title(forumPost.getTitle())
//        .content(forumPost.getContent())
//        .writeDate(forumPost.getWriteDate())
//        .state(forumPost.getState())
//        .build();
//  }
//
//  default ForumPost dtoToEntity(ForumPostDto forumPostDto) {
//
//    return ForumPost.builder()
//        .postId(forumPostDto.getPostId())
//        .title(forumPostDto.getTitle())
//        .content(forumPostDto.getContent())
//        .writeDate(forumPostDto.getWriteDate())
//        .state(forumPostDto.getState())
//        .build();
//  }
}
