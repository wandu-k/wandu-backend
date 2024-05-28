package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class BgmListDto {
  private Long bgmListId;
  private Long playlistId;
  private Long itemBuyId;

  @Builder
  public BgmListDto(Long itemBuyId, Long playlistId, Long bgmListId) {
    this.bgmListId = bgmListId;
    this.playlistId = playlistId;
    this.itemBuyId = itemBuyId;
  }

  /* 브금목록 */
  public BgmListDto() {
  }
}
