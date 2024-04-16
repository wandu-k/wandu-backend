package com.example.wandukong.dto.ShopInfo.Music;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.MyPlaylists;
import com.example.wandukong.domain.ShopInfo.Playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class MyPlaylistsDto {
  private Long userID;
  private Long myplID;

  /* 내 플레이리스트 */
  public MyPlaylists toEntity() {

    MyPlaylists myPlaylists = MyPlaylists.builder()
        .myplID(myplID)
        .userDo(UserDo.builder().userID(userID).build())
        .build();
    return myPlaylists;
  }

}