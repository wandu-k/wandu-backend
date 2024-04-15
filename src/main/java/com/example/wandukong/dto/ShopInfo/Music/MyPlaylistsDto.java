package com.example.wandukong.dto.ShopInfo.Music;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.MyPlaylists;
import com.example.wandukong.domain.Playlist;
import com.example.wandukong.domain.UserDo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MyPlaylistsDto {
  private Long userID;
  private Long myplID;
  private Long playlistID;

  /* 내 플레이리스트 */
  public MyPlaylists toEntity() {

    MyPlaylists myPlaylists = MyPlaylists.builder()
        .myplID(myplID)
        .userDo(UserDo.builder().userID(userID).build())
        .playlist(Playlist.builder().playlistID(playlistID).build())
        .build();
    return myPlaylists;
  }

}