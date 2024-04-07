package com.example.wandukong.dto.MiniHome;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MiniHomePostDto {
    private Long postID;
    private Long boardID;
    private Long userID;
    private Long hpID;
    private String title;
    private String content;
    private Date writeDay;

    @Builder
    public MiniHomePostDto(Long postID, Long boardID, Long userID, Long hpID, String title, String content,
            Date writeDay) {
        this.postID = postID;
        this.boardID = boardID;
        this.userID = userID;
        this.hpID = hpID;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }

}