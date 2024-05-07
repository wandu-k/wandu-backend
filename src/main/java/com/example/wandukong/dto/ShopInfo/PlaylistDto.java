package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.ShopInfo.Playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlaylistDto {

  private Long playlistId;
  private String plName;
  private Long musicBuyId;
  private Long hpId;

  /* 모든 사용자의 플레이리스트 */
  public Playlist toEntity() {

    Playlist playlist = Playlist.builder()
        .playlistId(playlistId)
        .miniHome(MiniHome.builder().hpId(hpId).build())
        .plName(plName)
        .build();
    return playlist;
  }

}