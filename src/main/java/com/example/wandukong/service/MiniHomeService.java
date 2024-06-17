package com.example.wandukong.service;

import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;

public interface MiniHomeService {

    MiniHomeDto getMiniHome(Long userId, Long likeUserId) throws HomeNotFoundException;

    void setMiniHomePlaylist(Long hpId, Long playlistId);

    void setMiniHome(Long userId, MiniHomeDto miniHomeDto);

}
