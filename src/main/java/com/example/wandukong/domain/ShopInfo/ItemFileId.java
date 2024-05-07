package com.example.wandukong.domain.ShopInfo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@Embeddable
public class ItemFileId implements Serializable {

  private String itemId;

  @Column(name = "itemfileId")
  private String itemfileId;
}