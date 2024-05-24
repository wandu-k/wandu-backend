package com.example.wandukong.controller.admin;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.service.MiniHomeBoardService;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/api/admin/minihome/board")
public class AdminMiniHomeBoardController {

    @Autowired
    MiniHomeBoardService miniHomeBoardservice;

    @PutMapping
    public ResponseEntity<?> putMiniHomeBoard(@RequestBody MiniHomeBoardDto MiniHomeBoardDto) {

        ApiResponse apiResponse = miniHomeBoardservice.putBoard(MiniHomeBoardDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMinihomeBoard(@RequestParam Long boardId) {

        miniHomeBoardservice.deleteMinihomeBoard(boardId);

        return new ResponseEntity<>("삭제 완료 되었습니다.", HttpStatus.OK);

    }

}
