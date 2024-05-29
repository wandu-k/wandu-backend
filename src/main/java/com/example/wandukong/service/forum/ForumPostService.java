package com.example.wandukong.service.forum;

import com.example.wandukong.dto.forum.ForumPostDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;

import com.example.wandukong.model.ApiResponseDto;

import static com.example.wandukong.exception.CustomException.*;

public interface ForumPostService {

  ForumPostDto get(Long postId) throws PostNotFoundException;

  ApiResponseDto modify(ForumPostDto forumPostDto) throws BoardNotFoundException;

  void remove(Long userId, Long postId) throws PostNotFoundException, PermissionDeniedException;

  PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto);

}
