package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.dto.page.PageRequestDto;

public interface ShopInfoPageRepository {

  Page<ShopInfoDto> SearchAndfindAll(PageRequestDto pageRequestDto, SearchItemDto searchDiaryDto);
}
