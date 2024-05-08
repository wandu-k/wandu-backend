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
  private Long bgmListId;
  private Long playlistId;
  private Long itemBuyId;

  /* 상점 */
  public BgmList toEntity() {

    BgmList bgmList = BgmList.builder()
        .bgmListId(bgmListId)
        .playlist(Playlist.builder().playlistId(playlistId).build())
        .buyItem(BuyItem.builder().itemBuyId(itemBuyId).build())
        .build();
    return bgmList;

  }
}
