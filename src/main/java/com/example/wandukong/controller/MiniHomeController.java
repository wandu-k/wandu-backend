package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.service.MiniHomeService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/minihome")
@RestController
public class MiniHomeController {

    @Autowired
    MiniHomeService miniHomeService;

    @GetMapping("/{userID}")
    public ResponseEntity<?> getMiniHome(@PathVariable Long userID) throws HomeNotFoundException {

        MiniHomeDto miniHomeDto = miniHomeService.getMiniHome(userID);
        return new ResponseEntity<>(miniHomeDto, HttpStatus.OK);
    }

    @GetMapping("/board")
    public ResponseEntity<?> getBoardList() {
        List<MiniHomeBoardDto> boardList = new ArrayList<MiniHomeBoardDto>();
        boardList = miniHomeService.getBoardList();

        if (boardList.isEmpty()) {
            return new ResponseEntity<>("현재 등록된 게시판이 없습니다", HttpStatus.OK);
        }
        return new ResponseEntity<>(boardList, HttpStatus.OK);
    }

}
