package com.example.wandukong.service.ask;

import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ask.AskDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.model.ApiResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AskService {

    AskDto get(Long askId) throws PostNotFoundException;

    ApiResponseDto modify(MultipartFile askFile, AskDto askDto) throws IOException;

    void remove(Long userId, Long askId) throws PostNotFoundException, PermissionDeniedException;

    PageResponseDto<AskDto> getList(PageRequestDto pageRequestDto);

    PageResponseDto<AskDto> AdminGetList(PageRequestDto pageRequestDto);
}
