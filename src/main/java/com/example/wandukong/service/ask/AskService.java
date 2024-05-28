package com.example.wandukong.service.ask;

import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ask.AskDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.model.ApiResponse;

public interface AskService {

    AskDto get(Long askId) throws PostNotFoundException;

    ApiResponse modify(AskDto askDto);

    void remove(Long userId, Long askId) throws PostNotFoundException, PermissionDeniedException;

    PageResponseDto<AskDto> getList(PageRequestDto pageRequestDto);
}
