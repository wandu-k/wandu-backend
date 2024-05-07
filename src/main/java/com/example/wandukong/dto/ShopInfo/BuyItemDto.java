package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class BuyItemDto {
  private Long itemBuyId;
  private Long userId;
  private Long itemId;
  private Long categoryId;

  /* 내 아바타 인벤토리 */
  public BuyItem toEntity() {

    BuyItem buyItem = BuyItem.builder()
        .itemBuyId(itemBuyId)
        .userDo(UserDo.builder().userId(userId).build())
        .shop(Shop.builder().itemId(itemId).build())
        .category(Category.builder().categoryId(categoryId).build())
        .build();
    return buyItem;
  }

}
