package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;

public interface BgmService {

    void addBgm(Long playlistId, Long itemId);

    List<ShopInfoDto> getBgmList(Long playlistId);

    void deleteBgm(Long playerlistId, Long itemId);

}
