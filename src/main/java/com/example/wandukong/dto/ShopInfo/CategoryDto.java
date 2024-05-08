package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.ShopInfo.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CategoryDto {
  private Long categoryId;
  private String categoryName;

  /* 상점 분류-음악, 아바타 부위분류 */
  public Category toEntity() {

    Category category = Category.builder()
        .categoryId(categoryId)
        .categoryName(categoryName)
        .build();
    return category;
  }

}