package com.example.wandukong.service.forum;

import com.example.wandukong.dto.forum.ForumPostDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;

import com.example.wandukong.model.ApiResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.wandukong.exception.CustomException.*;

public interface ForumPostService {

  ForumPostDto get(Long postId) throws PostNotFoundException;

  ApiResponseDto modify(MultipartFile postFile, ForumPostDto forumPostDto) throws BoardNotFoundException, IOException;

  void remove(Long userId, Long postId) throws PostNotFoundException, PermissionDeniedException;

  PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto);

}
