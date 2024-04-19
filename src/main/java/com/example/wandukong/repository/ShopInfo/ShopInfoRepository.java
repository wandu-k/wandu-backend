package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.PageRequestDto;

public class ShopInfoRepository {

  public Shop findByItemID(Long itemID) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findByItemID'");
  }

  public void save(Shop shop, Category category) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'save'");
  }

  public Page<Shop> findAll(PageRequestDto pageRequestDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'findAll'");
  }

}
