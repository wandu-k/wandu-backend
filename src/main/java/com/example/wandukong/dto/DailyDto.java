package com.example.wandukong.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DailyDto {

    private Long userId;
    private LocalDate date;

    public DailyDto(Long userId, LocalDate date) {
        this.userId = userId;
        this.date = date;
    }

    public DailyDto() {
    }

    

}
