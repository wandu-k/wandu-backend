package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.MiniHomeDto;
import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;

public interface MiniHomeService {

    MiniHomeDto getMiniHome(Long userID);

    List<MiniHomeBoardDto> getBoardList();

}
