package com.example.wandukong.dto.forum;

import com.example.wandukong.domain.forum.ForumBoard;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForumBoardDto {
    private Long boardId;
    private String boardName;

    public ForumBoard toEntity() {

        return ForumBoard.builder()
                .boardId(boardId)
                .boardName(boardName)
                .build();
    }
}
