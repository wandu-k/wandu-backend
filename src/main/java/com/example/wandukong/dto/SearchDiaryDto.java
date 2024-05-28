package com.example.wandukong.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class SearchDiaryDto {
    private LocalDate date;
    private Long userId;
}
