package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.ItemFile;
import com.example.wandukong.domain.Shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class ItemFileDto {
  private Long musicBuyID;
  private Long userID;
  private Long itemID;
  private String fileName;
  private Long uuid;

  /* 유저 음악 인벤토리 */
  public ItemFile toEntity() {

    ItemFile itemfile = ItemFile.builder()
        .shop(Shop.builder().itemID(itemID).build())
        .fileName(fileName)
        .uuid(uuid)
        .build();
    return itemfile;
  }

}
