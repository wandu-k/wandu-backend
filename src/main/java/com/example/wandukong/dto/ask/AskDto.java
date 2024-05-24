package com.example.wandukong.dto.ask;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class AskDto {

    private Long askId;

    private Long userId;

    private String title;

    private String content;

    private LocalDate writeDate;

    private int solveState;

    private int hideState;

    private int count;

    @Builder
    public AskDto(Long askId, Long userId, String title, String content, LocalDate writeDate, int solveState, int hideState, int count) {
        this.askId = askId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.writeDate = writeDate;
        this.solveState = solveState;
        this.hideState = hideState;
        this.count = count;
    }
}
