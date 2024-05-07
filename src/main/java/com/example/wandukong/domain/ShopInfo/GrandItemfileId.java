package com.example.wandukong.domain.ShopInfo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Embeddable
@EqualsAndHashCode
public class GrandItemfileId implements Serializable {
  private ItemFileId ItemfileId; // @MapsId("childId") 매핑

  @Column(name = "grandItemfileId")
  private String id;
}