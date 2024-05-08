package com.example.wandukong.dto.MiniHome;

import com.example.wandukong.domain.MiniHome.MiniHomeBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniHomeBoardDto {
    private Long boardId;
    private String boardName;

    public MiniHomeBoard toEntity() {

        MiniHomeBoard miniHomeBoard = MiniHomeBoard.builder()
                .boardId(boardId)
                .boardName(boardName)
                .build();

        return miniHomeBoard;
    }
}
