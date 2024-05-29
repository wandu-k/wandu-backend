package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.domain.ShopInfo.Category;

import lombok.Getter;

@Getter
public class CategoryDto {
  private Long categoryId;
  private String categoryName;

  public CategoryDto(Long categoryId, String categoryName) {
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }

  /* 상점 분류-음악, 아바타 부위분류 */
  public Category toEntity() {

    Category category = Category.builder()
        .categoryId(categoryId)
        .categoryName(categoryName)
        .build();
    return category;
  }

}