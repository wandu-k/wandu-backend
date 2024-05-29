package com.example.wandukong.service.ask;

import com.example.wandukong.dto.ask.AdminCommentDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;

public interface AdminCommentService {

    ApiResponseDto modify(AdminCommentDto adminCommentDto) throws PostNotFoundException;

    PageResponseDto<AdminCommentDto> getList(PageRequestDto pageRequestDto);

    void remove(Long userId, Long commentId) throws PostNotFoundException, PermissionDeniedException;
}
