package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import java.util.List;

public interface PlaylistService {

  List<PlaylistDto> getAllplaylist(Long userId);

  void putMyPlaylist(PlaylistDto playlistDto, Long playlistId, Long userId);

  void addPlayList(Long userId, PlaylistDto playlistDto);

  void deleteMyPlaylist(Long playlistId, Long userId);

  PlaylistDto getPlaylist(Long playlistId);

}
