package com.example.wandukong.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.ShopInfo.ShopDto;

public interface ShopService {
  int uploadItem(Long userID, MultipartFile Itemfile, ShopDto shopDto);
}
