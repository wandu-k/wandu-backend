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
  @Column(name = "bgmListID", unique = true)
  private Long bgmListID;

  @ManyToOne
  @JoinColumn(name = "itemBuyID", referencedColumnName = "itemBuyID")
  private BuyItem buyItem;

  @ManyToOne
  @JoinColumn(name = "playlistID", referencedColumnName = "playlistID")
  private Playlist playlist;
}
