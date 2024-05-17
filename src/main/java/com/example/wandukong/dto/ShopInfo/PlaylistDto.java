package com.example.wandukong.dto.ShopInfo;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.ShopInfo.Playlist;

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
  private Date plDate;

  @Builder
  public PlaylistDto(Long userId, Long playlistId, Long hpId, String plName, Date plDate) {
    this.playlistId = playlistId;
    this.plName = plName;
    this.hpId = hpId;
    this.userId = userId;
    this.plDate = plDate;
  }

  /* 각 사용자의 플레이리스트 */
  public Playlist toEntity() {

    Playlist playlist = Playlist.builder()
        .playlistId(playlistId)
        .miniHome(MiniHome.builder().hpId(hpId).build())
        .plName(plName)
        .plDate(plDate)
        .userDo(UserDo.builder().userId(userId).build())
        .build();
    return playlist;
  }

}