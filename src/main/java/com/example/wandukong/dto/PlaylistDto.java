package com.example.wandukong.dto;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.Playlist;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class PlaylistDto {
  private Long userID;
  private Long playlistID;
  private String plName;
  private Long hpID;

  /* 모든 사용자의 플레이리스트 */
  public Playlist toEntity() {

    Playlist playlist = Playlist.builder()
        .playlistID(playlistID)
        .userDo(UserDo.builder().userID(userID).build())
        .minihome(MiniHome.builder().hpID(hpID).build())
        .plName(plName)
        .build();
    return playlist;
  }

}