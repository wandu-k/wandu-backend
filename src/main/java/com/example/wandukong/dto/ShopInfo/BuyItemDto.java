package com.example.wandukong.dto.ShopInfo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BuyItemDto {

  private Long userId;
  private Long itemId;
  private LocalDate buyDate;

  @Builder
  public BuyItemDto(LocalDate buyDate, Long userId, Long itemId) {
    this.buyDate = buyDate;
    this.userId = userId;
    this.itemId = itemId;
  }

  /* 내 아이템 인벤토리 */
  public BuyItemDto() {
  }
}
