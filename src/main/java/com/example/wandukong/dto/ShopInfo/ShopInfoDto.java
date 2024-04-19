package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.UserDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ShopInfoDto {
  /* 상점 판매정보 */
  private ShopDto shopDto;
  private UserDto userDto;
  private ItemFileDto itemFileDto;
  private CategoryDto categoryDto;
  private Long userID;
  private String nickName;

  @Builder
  public ShopInfoDto(ShopDto shopDto, UserDto userDto, UserDo userDo, ItemFileDto itemFileDto, CategoryDto categoryDto,
      Long userID,
      String nickName) {
    this.shopDto = shopDto;
    this.userDto = userDto;
    this.itemFileDto = itemFileDto;
    this.categoryDto = categoryDto;
    this.nickName = userDto != null ? userDto.getNickname() : null;
  }

  public ShopInfoDto() {
    // TODO Auto-generated constructor stub
  }

}