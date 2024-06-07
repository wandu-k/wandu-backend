package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.InventoryItemDto;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.EntityAlreadyExistsException;

public interface InventoryItemService {

  PageResponseDto<InventoryItemDto> getMybuylist(Long userId, SearchItemDto searchItemDto);

  void addItem(BuyItemDto buyItemDto) throws EntityAlreadyExistsException;

}
