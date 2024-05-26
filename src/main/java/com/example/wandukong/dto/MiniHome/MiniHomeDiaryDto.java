package com.example.wandukong.dto.MiniHome;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MiniHomeDiaryDto {

    private Long postId;
    private Long userId;
    private Long hpId;
    private String title;
    private String content;
    private LocalDate writeDay;

    @Builder
    public MiniHomeDiaryDto(Long postId, Long userId, Long hpId, String title, String content,
            LocalDate writeDay) {
        this.postId = postId;
        this.userId = userId;
        this.hpId = hpId;
        this.title = title;
        this.content = content;
        this.writeDay = writeDay;
    }
}
