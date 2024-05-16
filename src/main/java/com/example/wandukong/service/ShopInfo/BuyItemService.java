package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;

public interface BuyItemService {

  PageResponseDto<BuyItemAllDto> getMybuylist(PageRequestDto pageRequestDto, UserDto user)
      throws UserNotFoundException;

  void purchaseItem(ShopInfoDto shopInfoDto, UserDto userDto) throws UserNotFoundException;

}
