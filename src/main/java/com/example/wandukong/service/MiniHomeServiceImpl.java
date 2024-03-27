package com.example.wandukong.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.MiniHome.MiniHomeBoard;
import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.repository.MiniHomeBoardRepository;
import com.example.wandukong.repository.MiniHomeRepository;

@Service
public class MiniHomeServiceImpl implements MiniHomeService {

    @Autowired
    MiniHomeRepository miniHomeRepository;

    @Autowired
    MiniHomeBoardRepository miniHomeBoardRepository;

    @Override
    public MiniHomeDto getMiniHome(Long userID) {

        MiniHome miniHome = miniHomeRepository.findByUserDo_UserID(userID);

        MiniHomeDto miniHomeDto = new MiniHomeDto();

        miniHomeDto.setUserID(miniHome.getUserDo().getUserID());
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
