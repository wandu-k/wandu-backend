package com.example.wandukong.controller.shopInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.BgmListNotFoundException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.ShopInfo.PlaylistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/api/user")
public class PlaylistController {

  @Autowired
  PlaylistService playlistService;

  // 사용자들의 플레이리스트 출력
  @SecurityRequirement(name = "Baerer Authentication")
  @GetMapping("/my/playlist")
  public ResponseEntity<?> getplaylist(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestParam SliceRequestDto sliceRequestDto,
      @RequestBody PlaylistAllDto playlistallDto) {

    // 미니홈을 접속했는지 안했는지의 여부는 프론트에서 확인합니다.

    Long userId = customUserDetails.getAccountDto().getUserId();

    List<PlaylistDto> responseDto = playlistService.getAllplaylist(userId);

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  // 사용자가 플레이 리스트 추가를 할 수 있어야됨.
  @PostMapping
  public ResponseEntity<?> addPlayList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestBody PlaylistDto playlistDto) {

    Long userId = customUserDetails.getAccountDto().getUserId();

    playlistService.addPlayList(userId, playlistDto);

    return new ResponseEntity<>("플레이리스트 생성 완료", HttpStatus.OK);
  }

  // 각 사용자의 플리를 추가/업데이트
  @Operation(summary = "플리 수정")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "플리 수정이 완료되었습니다."),
  })
  @SecurityRequirement(name = "Baerer Authentication")
  @PutMapping(value = "/{userId}/playlist/{playlistId}")
  public ResponseEntity<?> putMyPlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable Long userId,
      @PathVariable Long playlistId,
      @RequestBody PlaylistDto playlistDto)
      throws BadRequestException {

    if (!customUserDetails.getAccountDto().getUserId().equals(userId)) {
      throw new BadRequestException();
    }

    playlistService.putMyPlaylist(playlistDto, playlistId, userId);

    return new ResponseEntity<>("수정완료", HttpStatus.OK);

  }

  @DeleteMapping(value = "/{userId}/playlist/{playlistId}")
  public ResponseEntity<?> deleteMyPlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable Long userId,
      @PathVariable Long playlistId,
      @RequestBody PlaylistDto playlistDto)
      throws BadRequestException {

    if (!customUserDetails.getAccountDto().getUserId().equals(userId)) {
      throw new BadRequestException();
    }

    playlistService.deleteMyPlaylist(playlistDto, playlistId, userId);

    return new ResponseEntity<>("수정완료", HttpStatus.OK);

  }

}