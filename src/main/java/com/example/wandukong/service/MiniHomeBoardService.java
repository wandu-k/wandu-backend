package com.example.wandukong.service;

import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.model.ApiResponse;

public interface MiniHomeBoardService {

    ApiResponse putBoard(MiniHomeBoardDto miniHomeBoardDto);

    void deleteMinihomeBoard(Long boardId);

}
