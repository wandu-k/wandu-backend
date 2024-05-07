package com.example.wandukong.dto.MiniHome;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MiniHomePostDto {
    private Long postId = (long) 0;
    private Long boardId;
    private Long userId;
    private Long hpId;
    private String title;
    private String content;
    private LocalDate writeDay;

    @Builder
    public MiniHomePostDto(Long postId, Long boardId, Long userId, Long hpId, String title, String content,
            LocalDate writeDay) {
        this.postId = postId;
        this.boardId = boardId;
        this.userId = userId;
        this.hpId = hpId;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }

    public MiniHomePostDto() {
        // TODO Auto-generated constructor stub
    }

}
