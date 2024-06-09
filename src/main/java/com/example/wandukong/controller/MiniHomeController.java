package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.MiniHome.MiniHomeBoardDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.service.MiniHomeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/user")
@RestController
public class MiniHomeController {

    @Autowired
    MiniHomeService miniHomeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/minihome")
    public ResponseEntity<?> getMiniHome(@PathVariable Long userId) throws HomeNotFoundException {

        MiniHomeDto miniHomeDto = miniHomeService.getMiniHome(userId);
        return new ResponseEntity<>(miniHomeDto, HttpStatus.OK);
    }

    // @SecurityRequirement(name = "Bearer Authentication")
    // @GetMapping("/board")
    // public ResponseEntity<?> getBoardList() {
    // List<MiniHomeBoardDto> boardList = new ArrayList<MiniHomeBoardDto>();
    // boardList = miniHomeService.getBoardList();

    // if (boardList.isEmpty()) {
    // return new ResponseEntity<>("현재 등록된 게시판이 없습니다", HttpStatus.OK);
    // }
    // return new ResponseEntity<>(boardList, HttpStatus.OK);
    // }

    @Operation(summary = "미니홈 플레이리스트 설정")
    @PatchMapping("/minihome/playlist")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> setMiniHomePlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody Long playlistId) throws HomeNotFoundException {

        Long userId = customUserDetails.getAccountDto().getUserId();

        miniHomeService.setMiniHomePlaylist(userId, playlistId);
        return new ResponseEntity<>("미니홈 플레이리스트 설정 완료", HttpStatus.OK);
    }

    @Operation(summary = "미니홈 설정")
    @PatchMapping("/minihome")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> setMiniHome(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MiniHomeDto miniHomeDto) throws HomeNotFoundException {

        Long userId = customUserDetails.getAccountDto().getUserId();

        miniHomeService.setMiniHome(userId, miniHomeDto);
        return new ResponseEntity<>("미니홈 설정 완료", HttpStatus.OK);
    }

}
