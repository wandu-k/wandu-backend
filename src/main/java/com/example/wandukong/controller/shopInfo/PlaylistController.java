package com.example.wandukong.controller.shopInfo;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.exception.CustomException.BgmListNotFoundException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.model.ApiResponse;
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
      @RequestParam SliceRequestDto sliceRequestDto,
      @RequestBody PlaylistAllDto playlistallDto,
      @RequestBody UserDto userDto, @RequestBody MiniHomeDto miniHomeDto) throws UserNotFoundException {

    if (customUserDetails != null) {
      Long loginUserId = customUserDetails.getAccountDto().getUserId();
      Long minihomeUserId = miniHomeDto.getUserId();
      // 접속한 미니홈이 내 미니홈 일 경우
      if (loginUserId == minihomeUserId) {
        SliceResponseDto<PlaylistAllDto> responseDto = playlistService.getAllplaylist(sliceRequestDto, loginUserId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
      }
      SliceResponseDto<PlaylistAllDto> responseDto = playlistService.getAllplaylist(sliceRequestDto, minihomeUserId);
      // 접속한 미니홈이 내 미니홈이 아닐 경우
      return new ResponseEntity<>(responseDto, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("로그인후 이용해주세요", HttpStatus.UNAUTHORIZED);
    }

  }

  // 각 사용자의 플리를 추가/업데이트
  @SecurityRequirement(name = "Baerer Authentication")
  @PutMapping("/updatelist")
  public ResponseEntity<?> getUpdateMyPlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestParam PageRequestDto pageRequestDto, @RequestBody PlaylistAllDto playlistAllDto)
      throws BadRequestException, BgmListNotFoundException {

    if (customUserDetails.getAccountDto().getUserId() != playlistAllDto.getPlaylistDto().getUserId()) {

      throw new BadRequestException();
    }

    ApiResponse responseDto = playlistService.updateMyPlaylist(playlistAllDto);

    return new ResponseEntity<>(responseDto.getMessage(), responseDto.getStatus());

  }

}