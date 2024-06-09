package com.example.wandukong.dto.ShopInfo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaylistDto {

  private Long playlistId;
  private String plName;
  private Long userId;
  private LocalDate plDate;
  private int include;

  @Builder
  public PlaylistDto(Long playlistId, String plName, Long userId, LocalDate plDate, int include) {
    this.playlistId = playlistId;
    this.plName = plName;
    this.userId = userId;
    this.plDate = plDate;
    this.include = include;
  }

  public PlaylistDto() {
  }

}