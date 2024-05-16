package com.example.wandukong.dto.ShopInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BuyItemAllDto {
  private BuyItemDto buyItemDto;
  private ItemFileDto itemFileDto;
  private ShopInfoDto shopInfoDto;

  @Builder
  public BuyItemAllDto(BuyItemDto buyItemDto, ItemFileDto itemFileDto, ShopInfoDto shopInfoDto) {
    this.buyItemDto = buyItemDto;
    this.itemFileDto = itemFileDto;
  }

  public BuyItemAllDto() {

  }
}
