package com.example.wandukong.service.forum;

import com.example.wandukong.dto.forum.ForumBoardDto;
import com.example.wandukong.model.ApiResponse;

public interface ForumBoardService {

    ApiResponse modifyBoard(ForumBoardDto forumBoardDto);

    void removeBoard(Long boardId);
}
