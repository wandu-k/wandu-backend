package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;

public interface ShopInfoPageRepository {

  Page<ShopInfoDto> SearchAndfindAll(SearchItemDto searchDiaryDto);

  ShopInfoDto findByIdWithDto(Long itemId);
}
