package com.example.wandukong.dto.MiniHome;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MiniHomePostDto {
    private Long postID = (long) 0;
    private Long boardID;
    private Long userID;
    private Long hpID;
    private String title;
    private String content;
    private LocalDate writeDay;

    @Builder
    public MiniHomePostDto(Long postID, Long boardID, Long userID, Long hpID, String title, String content,
            LocalDate writeDay) {
        this.postID = postID;
        this.boardID = boardID;
        this.userID = userID;
        this.hpID = hpID;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }

    public MiniHomePostDto() {
        // TODO Auto-generated constructor stub
    }

}
