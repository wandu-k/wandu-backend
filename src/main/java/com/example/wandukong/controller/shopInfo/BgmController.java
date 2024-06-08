package com.example.wandukong.controller.shopInfo;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.service.BgmService;

import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/bgm")
public class BgmController {

    private final BgmService bgmService;

    @GetMapping("/{playlistId}")
    public ResponseEntity<?> getBgmList(@PathVariable Long playlistId) {

        List<ShopInfoDto> shopInfoDto = bgmService.getBgmList(playlistId);
        return new ResponseEntity<>(shopInfoDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> postBgmAdd(@RequestBody BgmListDto bgmListDto) {

        bgmService.addBgm(bgmListDto);

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping
    public String deleteBgm(@RequestBody String entity) {
        // TODO: process POST request

        return entity;
    }

}
