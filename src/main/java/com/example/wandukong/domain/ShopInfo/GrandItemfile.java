package com.example.wandukong.domain.ShopInfo;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "GrandItemfile")
public class GrandItemfile {
  @EmbeddedId
  private GrandItemfileId id;

  @MapsId("itemfileId") // GrandChildId.childId 매핑
  @OneToOne
  @JoinColumns({
      @JoinColumn(name = "itemId", referencedColumnName = "itemId"),
      @JoinColumn(name = "itemfileId", referencedColumnName = "itemfileId")
  })
  private ItemFile itemfile;

  private String name;
}
