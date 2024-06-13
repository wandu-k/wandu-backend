package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;

public interface MiniHomeService {

    MiniHomeDto getMiniHome(Long userId) throws HomeNotFoundException;

    void setMiniHomePlaylist(Long userId, Long playlistId);

    void setMiniHome(Long userId, MiniHomeDto miniHomeDto);

}
