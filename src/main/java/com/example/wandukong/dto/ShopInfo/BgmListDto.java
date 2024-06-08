package com.example.wandukong.dto.ShopInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BgmListDto {
  private Long bgmListId;
  private Long playlistId;
  private Long itemId;
  private Long userId;

  @Builder
  public BgmListDto(Long itemId, Long playlistId, Long bgmListId, Long userId) {
    this.bgmListId = bgmListId;
    this.playlistId = playlistId;
    this.itemId = itemId;
    this.userId = userId;
  }

  /* 브금목록 */
  public BgmListDto() {
  }
}
