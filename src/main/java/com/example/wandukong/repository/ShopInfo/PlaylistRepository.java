package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
