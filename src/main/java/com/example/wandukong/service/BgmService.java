package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;

public interface BgmService {

    void addBgm(BgmListDto bgmListDto);

    List<ShopInfoDto> getBgmList(Long playlistId);

}
