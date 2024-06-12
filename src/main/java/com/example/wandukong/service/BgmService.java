package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.ShopInfo.BgmListDto;

public interface BgmService {

    void addBgm(Long playlistId, Long buyItemId);

    List<BgmListDto> getBgmList(Long playlistId);

    void deleteBgm(Long playlistId, Long buyItemId);

}
