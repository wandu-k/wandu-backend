package com.example.wandukong.service;

import java.util.List;

import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;

public interface MiniHomeService {

    MiniHomeDto getMiniHome(Long userID) throws HomeNotFoundException;

    List<MiniHomeBoardDto> getBoardList();

}
