package com.example.wandukong.controller.shopInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.service.BgmService;
import com.example.wandukong.service.ShopInfo.PlaylistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "플레이리스트 관련 기능", description = "플레이리스트 기능 API")
@RestController
@RequestMapping("/api/user")
public class PlaylistController {

  @Autowired
  PlaylistService playlistService;

  @Autowired
  BgmService bgmService;

  // 특정 플레이리스트 정보 조회
  @Operation(summary = "특정 플레이리스트 조회")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/playlist/{playlistId}")
  public ResponseEntity<?> getPlaylist(@PathVariable Long playlistId)
      throws HomeNotFoundException {

    PlaylistDto playlistDto = playlistService.getPlaylist(playlistId);
    return new ResponseEntity<>(playlistDto, HttpStatus.OK);
  }

  @Operation(summary = "특정 플레이리스트의 노래 리스트")
  @SecurityRequirement(name = "Bearer Authentication")
  @GetMapping("/playlist/{playlistId}/bgm")
  public ResponseEntity<?> getBgmList(@PathVariable Long playlistId) {

    List<ShopInfoDto> shopInfoDto = bgmService.getBgmList(playlistId);
    return new ResponseEntity<>(shopInfoDto, HttpStatus.OK);
  }

  @Operation(summary = "특정 플레이리스트의 노래 삭제")
  @SecurityRequirement(name = "Baerer Authentication")
  @DeleteMapping("/playlist/{playlistId}/bgm/{itemId}")
  public ResponseEntity<?> deleteBgm(@PathVariable Long playlistId, @PathVariable Long itemId) {
    bgmService.deleteBgm(playlistId, itemId);

    return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
  }

  @Operation(summary = "특정 플레이리스트의 특정 노래 추가")
  @SecurityRequirement(name = "Baerer Authentication")
  @PostMapping("/playlist/{playlistId}/bgm/{itemId}")
  public ResponseEntity<?> postBgmAdd(@PathVariable Long playlistId, @PathVariable Long itemId) {

    bgmService.addBgm(playlistId, itemId);

    return new ResponseEntity<>("추가 완료", HttpStatus.OK);
  }

}