package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.EntityAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;

public interface InventoryItemService {

  PageResponseDto<BuyItemAllDto> getMybuylist(PageRequestDto pageRequestDto, Long userId)
      throws UserNotFoundException;

  void addItem(BuyItemDto buyItemDto) throws EntityAlreadyExistsException;

}
