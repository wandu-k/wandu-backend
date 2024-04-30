package com.example.wandukong.dto.ShopInfo;

import org.springframework.stereotype.Component;

import com.example.wandukong.domain.ShopInfo.ItemFile;
import com.example.wandukong.domain.ShopInfo.Shop;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@Builder
public class ItemFileDto {
  private Long musicBuyID;
  private Long userID;
  private Long itemID;
  private String fileName;
  private String uuid;

  /* 유저 음악 인벤토리 */
  public ItemFile toEntity() {

    ItemFile itemfile = ItemFile.builder()
        .itemID(Shop.builder().itemID(itemID).build())
        .fileName(fileName)
        .uuid(uuid)
        .build();
    return itemfile;
  }

}
