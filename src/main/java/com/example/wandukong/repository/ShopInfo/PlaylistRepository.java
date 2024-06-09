package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.ShopInfo.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long>, PlaylistAllpageRepository {

    List<Playlist> findAllByUserDo_UserId(Long userId);

    void deleteByPlaylistIdAndUserDo_userId(Long playlistId, Long userId);

    Playlist findByPlaylistIdAndUserDo_UserId(Long playlistId, Long userId);

}
