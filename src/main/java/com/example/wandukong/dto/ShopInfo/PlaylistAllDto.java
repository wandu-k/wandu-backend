package com.example.wandukong.dto.ShopInfo;

import com.example.wandukong.dto.UserDto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaylistAllDto {
  private UserDto userDto;
  private BgmListDto bgmListDto;
  private BuyItemDto buyItemDto;
  private PlaylistDto playlistDto;
  private ShopInfoDto shopInfoDto;

  @Builder
  public PlaylistAllDto(UserDto userDto,
      BuyItemDto buyItemDto,
      ShopInfoDto shopInfoDto,
      BgmListDto bgmListDto,
      PlaylistDto playlistDto) {
    this.userDto = userDto;
    this.buyItemDto = buyItemDto;
    this.bgmListDto = bgmListDto;
    this.shopInfoDto = shopInfoDto;
    this.playlistDto = playlistDto;
  }
}
