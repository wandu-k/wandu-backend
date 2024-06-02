package com.example.wandukong.dto.ShopInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopInfoDto {
  /* 상점 판매정보 */

  private Long userId;
  private Long itemId;
  private String nickname;
  private String itemName;
  private String subcategoryName;
  private int price;
  private String file;

  @Builder
  public ShopInfoDto(Long userId, Long itemId, String nickname, String itemName, String subcategoryName, int price,
      String file) {
    this.userId = userId;
    this.itemId = itemId;
    this.nickname = nickname;
    this.itemName = itemName;
    this.subcategoryName = subcategoryName;
    this.price = price;
    this.file = file;
  }
}