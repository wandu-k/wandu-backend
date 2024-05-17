package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;

public interface PlaylistAllpageRepository {
  List<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto);
}
