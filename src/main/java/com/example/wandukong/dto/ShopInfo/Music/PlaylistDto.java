package com.example.wandukong.dto.ShopInfo.Music;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.ShopInfo.Playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlaylistDto {

  private Long playlistID;
  private String plName;
  private Long musicBuyID;
  private Long hpID;

  /* 모든 사용자의 플레이리스트 */
  public Playlist toEntity() {

    Playlist playlist = Playlist.builder()
        .playlistID(playlistID)
        .miniHome(MiniHome.builder().hpID(hpID).build())
        .plName(plName)
        .build();
    return playlist;
  }

}