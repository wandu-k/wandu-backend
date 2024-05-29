package com.example.wandukong.service.forum;

import com.example.wandukong.dto.forum.ForumPostReviewDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;

public interface ForumPostReviewService {

    ApiResponseDto modify(ForumPostReviewDto forumPostReviewDto) throws PostNotFoundException;

    PageResponseDto<ForumPostReviewDto> getList(PageRequestDto pageRequestDto);

    void remove(Long userId, Long commentId) throws PostNotFoundException, PermissionDeniedException;
}
