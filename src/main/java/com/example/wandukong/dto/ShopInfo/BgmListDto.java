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
  private String url;

  @Builder
  public BgmListDto(Long playlistId, Long itemId, String album, String artist, String title, String url) {
    this.playlistId = playlistId;
    this.itemId = itemId;
    this.album = album;
    this.artist = artist;
    this.title = title;
    this.url = url;
  }

  /* 브금목록 */
  public BgmListDto() {
  }
}
