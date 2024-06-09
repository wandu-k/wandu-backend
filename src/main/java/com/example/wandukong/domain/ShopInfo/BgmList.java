package com.example.wandukong.domain.ShopInfo;

import com.example.wandukong.domain.BgmListPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "BgmList")
public class BgmList {

  @EmbeddedId
  @Column(name = "bgmListId", unique = true)
  private BgmListPK bgmListId;

  public BgmList(BgmListPK bgmListId) {
    this.bgmListId = bgmListId;
  }
}
