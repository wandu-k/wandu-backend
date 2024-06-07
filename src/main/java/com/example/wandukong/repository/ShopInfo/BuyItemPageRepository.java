package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.dto.InventoryItemDto;
import com.example.wandukong.dto.SearchItemDto;

public interface BuyItemPageRepository {

  Page<InventoryItemDto> findByUserDoUserId(Long userId, SearchItemDto searchItemDto);

}
