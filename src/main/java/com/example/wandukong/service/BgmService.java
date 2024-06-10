package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.ShopInfo.BgmListDto;

public interface BgmService {

    void addBgm(Long playlistId, Long itemId);

    List<BgmListDto> getBgmList(Long playlistId);

    void deleteBgm(Long playlistId, Long itemId);

}
