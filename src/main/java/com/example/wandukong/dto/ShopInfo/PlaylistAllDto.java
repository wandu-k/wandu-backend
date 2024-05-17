package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.dto.UserDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaylistAllDto {
  private UserDto userDto;
  private BuyItemDto buyItemDto;
  private PlaylistDto playlistDto;
  private ShopInfoDto shopInfoDto;

  @Builder
  public PlaylistAllDto(UserDto userDto,
      BuyItemDto buyItemDto,
      PlaylistDto playlistDto,
      ShopInfoDto shopInfoDto) {
    this.userDto = userDto;
    this.buyItemDto = buyItemDto;
    this.playlistDto = playlistDto;
    this.shopInfoDto = shopInfoDto;
  }
}
