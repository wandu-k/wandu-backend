package com.example.wandukong.service.forum;

import com.example.wandukong.dto.forum.ForumBoardDto;
import com.example.wandukong.model.ApiResponseDto;

public interface ForumBoardService {

    ApiResponseDto modifyBoard(ForumBoardDto forumBoardDto);

    void removeBoard(Long boardId);
}
