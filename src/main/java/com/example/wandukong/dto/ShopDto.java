package com.example.wandukong.dto;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.Category;
import com.example.wandukong.domain.Shop;
import com.example.wandukong.domain.UserDo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ShopDto {
  private Long userID;
  private Long itemID;
  private String itemName;
  private Long categoryID;

  /* 상점 */
  public Shop toEntity() {

    Shop shop = Shop.builder()
        .itemID(itemID)
        .itemName(itemName)
        .userDo(UserDo.builder().userID(userID).build())
        .category(Category.builder().categoryID(categoryID).build())
        .build();
    return shop;
  }

}