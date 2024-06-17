package com.example.wandukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;
import com.example.wandukong.repository.miniHome.MiniHomeBoardRepository;
import com.example.wandukong.repository.miniHome.MiniHomeRepository;
import com.example.wandukong.util.GetIpUtil;

import jakarta.transaction.Transactional;

@Service
public class MiniHomeServiceImpl implements MiniHomeService {

    @Autowired
    MiniHomeRepository miniHomeRepository;

    @Autowired
    MiniHomeBoardRepository miniHomeBoardRepository;

    @Autowired
    PlaylistRepository playlistRepository;

    @Autowired
    GetIpUtil getIpUtil;

    @Transactional
    @Override
    public MiniHomeDto getMiniHome(Long userId, Long likeUserId) throws HomeNotFoundException {


        MiniHomeDto miniHomeDto = miniHomeRepository.findByUserDo_UserId(userId, likeUserId);


        return miniHomeDto;
    }

    @Override
    public void setMiniHomePlaylist(Long hpId, Long playlistId) {

        MiniHome miniHome = miniHomeRepository.findById(playlistId).orElseThrow();

        if (miniHome.getUserDo().getUserId() != hpId) {
            return;
        }

        Playlist playlist = null;
        if (playlistId != null) {
            playlist = playlistRepository.getReferenceById(playlistId);
        }

        miniHome.updatePlaylist(playlist);
        miniHomeRepository.save(miniHome);

    }

    @Override
    public void setMiniHome(Long hpId, MiniHomeDto miniHomeDto) {

        MiniHome miniHome = miniHomeRepository.findById(hpId).orElseThrow();

        miniHome.updateMiniHome(miniHomeDto);

        miniHomeRepository.save(miniHome);

    }

}
