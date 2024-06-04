package com.example.wandukong.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class SearchDiaryDto {
    private LocalDate date;
    private Long postId;
    private Long userId;

    public SearchDiaryDto(LocalDate date, Long postId, Long userId) {
        this.date = date;
        this.postId = postId;
        this.userId = userId;
    }

}
