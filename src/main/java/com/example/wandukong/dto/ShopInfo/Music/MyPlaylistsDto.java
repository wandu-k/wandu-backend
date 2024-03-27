package com.example.wandukong.dto.ShopInfo.Music;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.MyBgm;
import com.example.wandukong.domain.MyPlaylists;
import com.example.wandukong.domain.Playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MyPlaylistsDto {
  private Long musicBuyID;
  private Long myplID;
  private Long playlistID;

  /* 내 플레이리스트 */
  public MyPlaylists toEntity() {

    MyPlaylists mybgm = MyPlaylists.builder()
        .myplID(myplID)
        .mybgm(MyBgm.builder().musicBuyID(musicBuyID).build())
        .playlist(Playlist.builder().playlistID(playlistID).build())
        .build();
    return mybgm;
  }

}