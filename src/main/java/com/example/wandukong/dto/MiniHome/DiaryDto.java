package com.example.wandukong.dto.MiniHome;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryDto {

    private Long postId;
    private Long userId;
    private String title;
    private String content;
    private LocalDate writeDay;

    @Builder
    public DiaryDto(Long postId, Long userId, String title, String content,
            LocalDate writeDay) {
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }
}
