package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.dto.AccountDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopInfoDto {
  /* 상점 판매정보 */
  private ShopDto shopDto;
  private ItemFileDto itemFileDto;
  private CategoryDto categoryDto;
  private String nickName;

  @Builder
  public ShopInfoDto(ShopDto shopDto, AccountDto userDto, ItemFileDto itemFileDto, String categoryName,
      CategoryDto categoryDto,
      String nickName) {
    this.shopDto = shopDto;
    this.itemFileDto = itemFileDto;
    this.categoryDto = categoryDto;
    this.nickName = userDto != null ? userDto.getNickname() : null;
  }

  public ShopInfoDto() {
    // TODO Auto-generated constructor stub
  }

}