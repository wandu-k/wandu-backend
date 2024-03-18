package com.example.wandukong.dto;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.MyAvatar;
import com.example.wandukong.domain.Shop;
import com.example.wandukong.domain.UserDo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MyAvatarDto {
  private Long itemBuyID;
  private Long userID;
  private Long itemID;

  /* DTO -> Entity */
  public MyAvatar toEntity() {

    MyAvatar myavatar = MyAvatar.builder()
        .itemBuyID(itemBuyID)
        .userDo(UserDo.builder().userID(userID).build())
        .shop(Shop.builder().itemID(itemID).build())
        .build();
    return myavatar;
  }

}
