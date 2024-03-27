package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;

public interface MiniHomeService {

    MiniHomeDto getMiniHome(Long userID);

    List<MiniHomeBoardDto> getBoardList();

}
