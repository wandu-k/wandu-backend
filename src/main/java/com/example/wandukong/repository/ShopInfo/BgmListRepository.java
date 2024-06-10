package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.BgmListPK;
import com.example.wandukong.domain.ShopInfo.BgmList;

@Repository
public interface BgmListRepository extends JpaRepository<BgmList, BgmListPK> {

    List<BgmList> findAllByBgmListId_Playlist_PlaylistId(Long playlistId);

}
