package com.example.wandukong.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
    private Long categoryId;
    private Long buyItemId;

}