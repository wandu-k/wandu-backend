package com.example.wandukong.dto.ShopInfo;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Builder;
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
  private Date buyDate;

  @Builder
  public BuyItemDto(Long itemBuyId, Date buyDate, Long categoryId, Long userId, Long itemId) {
    this.itemBuyId = itemBuyId;
    this.buyDate = buyDate;
    this.categoryId = categoryId;
    this.userId = userId;
    this.itemId = itemId;
  }

  /* 내 아이템 인벤토리 */
  public BuyItemDto() {
  }
}
