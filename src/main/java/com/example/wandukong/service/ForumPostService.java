package com.example.wandukong.service;

import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;

import com.example.wandukong.model.ApiResponse;

import static com.example.wandukong.exception.CustomException.*;

public interface ForumPostService {

  ForumPostDto get(Long postId) throws PostNotFoundException;

  ApiResponse modify(ForumPostDto forumPostDto) throws BoardNotFoundException;

  void remove(Long userId, Long postId) throws PostNotFoundException, PermissionDeniedException;

  PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto);

}
