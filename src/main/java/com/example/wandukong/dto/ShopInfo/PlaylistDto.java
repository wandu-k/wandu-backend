package com.example.wandukong.dto.ShopInfo;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlaylistDto {

  private Long playlistId;
  private String plName;
  private Long userId;
  private LocalDate plDate;
  private int include;

  @Builder
  public PlaylistDto(Long userId, Long playlistId, String plName, LocalDate plDate, int include) {
    this.playlistId = playlistId;
    this.plName = plName;
    this.userId = userId;
    this.plDate = plDate;
    this.include = include;
  }

  public PlaylistDto() {
  }

}