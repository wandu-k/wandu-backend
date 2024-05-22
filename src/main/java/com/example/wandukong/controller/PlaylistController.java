package com.example.wandukong.controller;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.service.ShopInfo.PlaylistService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {

  @Autowired
  PlaylistService playlistService;

  // 사용자들의 플레이리스트 출력
  @SecurityRequirement(name = "Baerer Authentication")
  @GetMapping("/list")
  public ResponseEntity<?> getplaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestParam SliceRequestDto sliceRequestDto, @PathVariable Long userId,
      @RequestBody PlaylistAllDto playlistallDto,
      @RequestBody UserDto userDto) throws UserNotFoundException {

    if (customUserDetails != null) {
      SliceResponseDto<PlaylistAllDto> responseDto = playlistService.getAllplaylist(sliceRequestDto, userDto);

      return new ResponseEntity<>(responseDto, HttpStatus.OK);

    } else {
      return new ResponseEntity<>("로그인후 이용해주세요", HttpStatus.UNAUTHORIZED);
    }

  }

  // 각 사용자의 플리를 업데이트
  @SecurityRequirement(name = "Baerer Authentication")
  @GetMapping("/updatelist")
  public ResponseEntity<?> getUpdateMyPlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestParam PageRequestDto pageRequestDto, @PathVariable Long userId, @RequestBody PlaylistDto playlistDto,
      @RequestBody UserDto userDto) throws UserNotFoundException {

    if (customUserDetails.getUserDto().getUserId() != playlistDto.getUserId()) {

      throw new BadRequestException();
    }

    PageResponseDto<PlaylistDto> responseDto = playlistService.updateMyPlaylist(pageRequestDto, userDto);

    return new ResponseEntity<>(responseDto, HttpStatus.OK);

  }

}