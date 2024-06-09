package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import com.example.wandukong.dto.ShopInfo.PlaylistDto;

public interface PlaylistAllpageRepository {

  List<PlaylistDto> findAllPlaylist(Long userId, Long itemId);
}
