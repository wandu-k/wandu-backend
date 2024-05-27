package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;

public interface PlaylistAllpageRepository {
  Page<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto, Long userId);

  Page<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto, Long lastId, Long userId);
}
