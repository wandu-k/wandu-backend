package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.Shop;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Builder
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