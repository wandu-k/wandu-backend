package com.example.wandukong.dto.ShopInfo;

import lombok.Getter;

@Getter
public class ShopSubCategoryDto {
    private Long subcategoryId;
    private String subcategoryName;
    private Long categoryId;
    private String categoryName;

    public ShopSubCategoryDto(Long subcategoryId, String subcategoryName, Long categoryId, String categoryName) {
        this.subcategoryId = subcategoryId;
        this.subcategoryName = subcategoryName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
