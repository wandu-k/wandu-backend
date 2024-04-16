package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MyAvatarDto {
  private Long itemBuyID;
  private Long userID;
  private Long itemID;

  /* 내 아바타 인벤토리 */
  public BuyItem toEntity() {

    BuyItem myavatar = BuyItem.builder()
        .itemBuyID(itemBuyID)
        .userDo(UserDo.builder().userID(userID).build())
        .shop(Shop.builder().itemID(itemID).build())
        .build();
    return myavatar;
  }

}
