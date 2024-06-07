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
  private String categoryName;
  private String subcategoryName;
  private Long subcategoryId;
  private int price;
  private String file;
  private int purchaseStatus;
  private int purchase;

  @Builder
  public ShopInfoDto(Long userId, Long itemId, String nickname, String itemName, String categoryName,
      String subcategoryName, int price, Long subcategoryId,
      String file, int purchaseStatus, int purchase) {
    this.userId = userId;
    this.itemId = itemId;
    this.nickname = nickname;
    this.itemName = itemName;
    this.categoryName = categoryName;
    this.subcategoryName = subcategoryName;
    this.price = price;
    this.subcategoryId = subcategoryId;
    this.file = file;
    this.purchaseStatus = purchaseStatus;
    this.purchase = purchase;
  }

  public void setFile(String file) {
    this.file = file;
  }

}