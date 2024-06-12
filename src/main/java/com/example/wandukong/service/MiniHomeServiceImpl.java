package com.example.wandukong.service;

import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.MiniHome.MiniHomeBoard;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;
import com.example.wandukong.repository.miniHome.MiniHomeBoardRepository;
import com.example.wandukong.repository.miniHome.MiniHomeRepository;
import com.example.wandukong.util.GetIpUtil;

import jakarta.servlet.http.HttpServletRequest;
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
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    GetIpUtil getIpUtil;

    @Transactional
    @Override
    public MiniHomeDto getMiniHome(Long userId) throws HomeNotFoundException {

        MiniHome miniHome = miniHomeRepository.findByUserDo_UserId(userId);

        if (miniHome == null) {
            throw new HomeNotFoundException();
        }

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String userIp = getIpUtil.getIp(request);
        String userAgent = request.getHeader("User-Agent");
        String visitorkey = "visit" + ":" + miniHome.getHpId() + ":" + userIp;
        String viewKey = "view" + ":" + miniHome.getHpId();

        if (!redisTemplate.opsForValue().getOperations().hasKey(visitorkey)) {
            redisTemplate.opsForValue().set(visitorkey, userAgent, Duration.ofHours(24));
            redisTemplate.opsForValue().increment(viewKey);
            miniHome.viewCount(miniHome.getAllVisit() + 1);
        }

        MiniHomeDto miniHomeDto = miniHome.toDto(Integer.parseInt(redisTemplate.opsForValue().get(viewKey)));

        return miniHomeDto;
    }

    @Override
    public List<MiniHomeBoardDto> getBoardList() {

        List<MiniHomeBoard> miniHomeBoards = miniHomeBoardRepository.findAll();

        List<MiniHomeBoardDto> boardList = new ArrayList<>();

        for (MiniHomeBoard miniHomeBoard : miniHomeBoards) {
            MiniHomeBoardDto miniHomeBoardDto = new MiniHomeBoardDto();
            miniHomeBoardDto.setBoardId(miniHomeBoard.getBoardId());
            miniHomeBoardDto.setBoardId(miniHomeBoard.getBoardId());
            miniHomeBoardDto.setBoardName(miniHomeBoard.getBoardName());
            boardList.add(miniHomeBoardDto);
        }

        return boardList;
    }

    @Override
    public void setMiniHomePlaylist(Long userId, Long playlistId) {

        MiniHome miniHome = miniHomeRepository.findByUserDo_UserId(userId);

        Playlist playlist = null;
        if (playlistId != null) {
            playlist = playlistRepository.getReferenceById(playlistId);
        }

        miniHome.updatePlaylist(playlist);
        miniHomeRepository.save(miniHome);

    }

    @Override
    public void setMiniHome(Long userId, MiniHomeDto miniHomeDto) {

        MiniHome miniHome = miniHomeRepository.findByUserDo_UserId(userId);

        miniHome.updateMiniHome(miniHomeDto);

        miniHomeRepository.save(miniHome);

    }

}
