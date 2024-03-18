package com.example.wandukong.dto;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.MyBgm;
import com.example.wandukong.domain.Shop;
import com.example.wandukong.domain.UserDo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MyBgmDto {
  private Long musicBuyID;
  private Long userID;
  private Long itemID;

  /* 유저 음악 인벤토리 */
  public MyBgm toEntity() {

    MyBgm mybgm = MyBgm.builder()
        .musicBuyID(musicBuyID)
        .userDo(UserDo.builder().userID(userID).build())
        .shop(Shop.builder().itemID(itemID).build())
        .build();
    return mybgm;
  }

}