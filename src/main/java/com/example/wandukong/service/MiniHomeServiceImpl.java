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
import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
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
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    GetIpUtil getIpUtil;

    @Transactional
    @Override
    public MiniHomeDto getMiniHome(Long hpID) throws HomeNotFoundException {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String userIp = getIpUtil.getIp(request);
        String userAgent = request.getHeader("User-Agent");
        String visitorkey = "visit" + ":" + hpID + ":" + userIp;
        String viewKey = "view" + ":" + hpID;

        if (!redisTemplate.opsForValue().getOperations().hasKey(visitorkey)) {
            redisTemplate.opsForValue().set(visitorkey, userAgent, Duration.ofHours(24));
            redisTemplate.opsForValue().increment(viewKey);
        }

        MiniHome miniHome = miniHomeRepository.findById(hpID)
                .orElseThrow(() -> new HomeNotFoundException());

        miniHome.viewCount(miniHome.getAllVisit() + 1);

        MiniHomeDto miniHomeDto = MiniHomeDto.builder()
                .userID(miniHome.getUserDo().getUserID())
                .hpID(miniHome.getHpID())
                .statusM(miniHome.getStatusM())
                .introduction(miniHome.getIntroduction())
                .hpToday(Integer.parseInt(redisTemplate.opsForValue().get(viewKey)))
                .allVisit(miniHome.getAllVisit())
                .build();

        return miniHomeDto;
    }

    @Override
    public List<MiniHomeBoardDto> getBoardList() {

        List<MiniHomeBoard> miniHomeBoards = miniHomeBoardRepository.findAll();

        List<MiniHomeBoardDto> boardList = new ArrayList<>();

        for (MiniHomeBoard miniHomeBoard : miniHomeBoards) {
            MiniHomeBoardDto miniHomeBoardDto = new MiniHomeBoardDto();
            miniHomeBoardDto.setBoardID(miniHomeBoard.getBoardID());
            miniHomeBoardDto.setBoardName(miniHomeBoard.getBoardName());
        }

        return boardList;
    }

}
