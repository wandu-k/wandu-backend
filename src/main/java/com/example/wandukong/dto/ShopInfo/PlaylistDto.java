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
  private Long hpId;
  private Long userId;
  private LocalDate plDate;

  @Builder
  public PlaylistDto(Long userId, Long playlistId, Long hpId, String plName, LocalDate plDate) {
    this.playlistId = playlistId;
    this.plName = plName;
    this.hpId = hpId;
    this.userId = userId;
    this.plDate = plDate;
  }

  public PlaylistDto() {
  }

}