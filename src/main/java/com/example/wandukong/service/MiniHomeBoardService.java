package com.example.wandukong.service;

import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.model.ApiResponseDto;

public interface MiniHomeBoardService {

    ApiResponseDto putBoard(MiniHomeBoardDto miniHomeBoardDto);

    void deleteMinihomeBoard(Long boardId);

}
