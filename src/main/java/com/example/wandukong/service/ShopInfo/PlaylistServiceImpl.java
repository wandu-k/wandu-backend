package com.example.wandukong.service.ShopInfo;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;

import jakarta.transaction.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  @Autowired
  PlaylistRepository playlistRepository;

  @Override
  public List<PlaylistDto> getAllplaylist(Long userId) {

    List<Playlist> playlists = playlistRepository.findAllByUserDo_UserId(userId);

    List<PlaylistDto> playlistDtos = new ArrayList<>();

    for (Playlist playlist : playlists) {

      PlaylistDto playlistDto = PlaylistDto.builder()
          .playlistId(playlist.getPlaylistId())
          .plName(playlist.getPlName())
          .userId(playlist.getPlaylistId())
          .plDate(playlist.getPlDate())
          .build();

      playlistDtos.add(playlistDto);
    }

    return playlistDtos;
  }

  @Transactional
  @Override
  public void putMyPlaylist(PlaylistDto playlistDto, Long playlistId, Long userId) {

    Playlist playlist = playlistRepository.findByPlaylistIdAndUserDo_UserId(playlistId, userId);

    playlist.updatePlaylist(playlistDto.getPlName());
  }

  // 플레이어 생성
  @Override
  public void addPlayList(Long userId, PlaylistDto playlistDto) {
    Playlist playlist = Playlist.builder()
        .userDo(UserDo.builder()
            .userId(userId).build())
        .plName(playlistDto.getPlName())
        .build();

    playlistRepository.save(playlist);
  }

  @Transactional
  @Override
  public void deleteMyPlaylist(Long playlistId, Long userId) {
    playlistRepository.deleteByPlaylistIdAndUserDo_userId(playlistId, userId);
  }

  @Override
  public PlaylistDto getPlaylist(Long playlistId) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(null);

    PlaylistDto playlistDto = PlaylistDto.builder().playlistId(playlist.getPlaylistId())
        .plName(playlist.getPlName()).plDate(playlist.getPlDate()).build();

    return playlistDto;
  }
}
