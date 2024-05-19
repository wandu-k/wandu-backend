package com.example.wandukong.service;

import com.example.wandukong.dto.ForumBoardDto;
import com.example.wandukong.model.ApiResponse;

public interface ForumBoardService {

    ApiResponse modifyBoard(ForumBoardDto forumBoardDto);

    void deleteBoard(Long boardId);
}
