package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class BgmListDto {
  private Long bgmListID;
  private Long playlistID;
  private Long itemBuyID;

  /* 상점 */
  public BgmList toEntity() {

    BgmList bgmList = BgmList.builder()
        .bgmListID(bgmListID)
        .playlist(Playlist.builder().playlistID(playlistID).build())
        .buyItem(BuyItem.builder().itemBuyID(itemBuyID).build())
        .build();
    return bgmList;

  }
}
