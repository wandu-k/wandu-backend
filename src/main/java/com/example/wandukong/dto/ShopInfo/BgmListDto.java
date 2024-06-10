package com.example.wandukong.dto.ShopInfo;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BgmListDto {
  private Long playlistId;
  private Long itemId;
  private String album;
  private String artist;
  private String title;

  @Builder
  public BgmListDto(Long playlistId, Long itemId, String album, String artist, String title) {
    this.playlistId = playlistId;
    this.itemId = itemId;
    this.album = album;
    this.artist = artist;
    this.title = title;
  }

  /* 브금목록 */
  public BgmListDto() {
  }
}
