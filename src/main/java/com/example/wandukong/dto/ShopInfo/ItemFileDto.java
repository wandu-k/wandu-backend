package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Component
@NoArgsConstructor
public class ItemFileDto {
  private Long itemId;
  private String fileName;
  private String uuid;

  @Builder
  public ItemFileDto(Long itemId, String uuid, String fileName) {
    this.itemId = itemId;
    this.uuid = uuid;
    this.fileName = fileName;
  }

}
