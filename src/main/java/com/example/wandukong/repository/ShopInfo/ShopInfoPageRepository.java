package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.PageRequestDto;

public interface ShopInfoPageRepository {

  Page<Shop> findAllByCategoryAndItemFileIsNotNull(PageRequestDto pageRequestDto);

  Page<Shop> findAllByCategoryAndItemFileIsNotNull(PageRequestDto pageRequestDto, UserDo userID);
}
