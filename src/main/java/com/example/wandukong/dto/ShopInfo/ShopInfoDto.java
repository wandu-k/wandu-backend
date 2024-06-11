package com.example.wandukong.dto.ShopInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
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
  private Long categoryId;
  private String thumbnail;
}