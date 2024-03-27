package com.example.wandukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.domain.Shop;
import com.example.wandukong.dto.ShopDto;

@Service
public class ShopServiceImpl {

  @Autowired
  ShopRepository shoprepository;

  @Override
  public int uploadItem(Long userID, MultipartFile Itemfile, ShopDto shopDto) {

    Shop shop = ShopRepository.findByUserDo_UserID(userID);

    ShopDto shopDto = new ShopDto();

    ShopDto.setUserID(shop.getUserDo().getUserID());
    return shopDto;
  }
}
