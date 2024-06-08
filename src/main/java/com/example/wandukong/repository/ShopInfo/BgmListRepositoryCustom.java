package com.example.wandukong.repository.ShopInfo;

import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import java.util.List;

public interface BgmListRepositoryCustom {

    List<ShopInfoDto> findAllByPlaylistId(Long playlistId);

}
