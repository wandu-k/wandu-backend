package com.example.wandukong.dto;

import lombok.Getter;

@Getter
public class MyStatisticsDto {

    private int uploadCount;
    private int sellCount;
    // private int itemLike;
    private int buyCount;

    public MyStatisticsDto(int uploadCount, int sellCount, int buyCount) {
        this.uploadCount = uploadCount;
        this.sellCount = sellCount;
        // this.itemLike = itemLike;
        this.buyCount = buyCount;
    }
}
