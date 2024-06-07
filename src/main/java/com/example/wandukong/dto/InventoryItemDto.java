package com.example.wandukong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class InventoryItemDto {
    /* 상점 판매정보 */

    private Long userId;
    private Long itemId;
    private String nickname;
    private String itemName;
    private String categoryName;
    private String subcategoryName;
    private Long subcategoryId;
    private int price;
    private String file;
    private int enable;

    @Builder
    public InventoryItemDto(Long userId, Long itemId, String nickname, String itemName, String categoryName,
            String subcategoryName, int price, Long subcategoryId,
            String file, int enable) {
        this.userId = userId;
        this.itemId = itemId;
        this.nickname = nickname;
        this.itemName = itemName;
        this.categoryName = categoryName;
        this.subcategoryName = subcategoryName;
        this.price = price;
        this.subcategoryId = subcategoryId;
        this.file = file;
        this.enable = enable;
    }
}