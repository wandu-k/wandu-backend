package com.example.wandukong.domain.ShopInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "BgmList")
public class BgmList {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bgmListId", unique = true)
  private Long bgmListId;

  @ManyToOne
  @JoinColumn(name = "itemBuyId", referencedColumnName = "itemBuyId")
  private BuyItem buyItem;

  @ManyToOne
  @JoinColumn(name = "playlistId", referencedColumnName = "playlistId")
  private Playlist playlist;
}
