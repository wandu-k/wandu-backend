package com.example.wandukong.dto;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CategoryDto {
  private Long categoryID;
  private String categoryName;

  /* 상점 분류-음악, 아바타 부위분류 */
  public Category toEntity() {

    Category category = Category.builder()
        .categoryID(categoryID)
        .categoryName(categoryName)
        .build();
    return category;
  }

}