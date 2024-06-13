package com.example.wandukong.service.ShopInfo;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;

public interface ShopService {

        PageResponseDto<ShopInfoDto> getShopItemList(SearchItemDto searchItemDto);

        ShopInfoDto getItem(Long itemId, Long userId);

        void addItem(MultipartFile itemfile, ShopDto shopDto) throws IOException;
}
