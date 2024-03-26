package com.example.wandukong.dto.MiniHome;

import com.example.wandukong.domain.MiniHome.MiniHomeBoard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniHomeBoardDto {
    private Long boardID;
    private String boardName;

    public MiniHomeBoard toEntity() {

        MiniHomeBoard miniHomeBoard = MiniHomeBoard.builder()
                .boardID(boardID)
                .boardName(boardName)
                .build();

        return miniHomeBoard;
    }
}
