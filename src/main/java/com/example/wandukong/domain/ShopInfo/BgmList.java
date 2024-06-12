package com.example.wandukong.domain.ShopInfo;

import com.example.wandukong.domain.BgmListPK;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "BgmList")
public class BgmList {

  @EmbeddedId
  private BgmListPK bgmListId;

  @MapsId("buyItemId")
  @ManyToOne(fetch = FetchType.LAZY)
  private BuyItem buyItem;

  @MapsId("playlistId") // 복합 키를 사용하는 경우에만 @MapsId를 사용합니다.
  @ManyToOne(fetch = FetchType.LAZY)
  private Playlist playlist;

  public BgmList(BgmListPK bgmListId, BuyItem buyItem, Playlist playlist) {
    this.bgmListId = bgmListId;
    this.buyItem = buyItem;
    this.playlist = playlist;
  }
}