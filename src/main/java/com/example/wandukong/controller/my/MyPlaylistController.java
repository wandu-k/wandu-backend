package com.example.wandukong.controller.my;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.BgmService;
import com.example.wandukong.service.ShopInfo.PlaylistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "내 플레이리스트", description = "내 플레이리스트 관리 API")
@RequiredArgsConstructor
@RequestMapping("/api/my/playlist")
@RestController
public class MyPlaylistController {

    private final PlaylistService playlistService;
    private final BgmService bgmService;

    // 사용자들의 플레이리스트 출력
    @Operation(summary = "나의 플레이리스트 목록 조회")
    @SecurityRequirement(name = "Baerer Authentication")
    @GetMapping
    public ResponseEntity<?> getplaylist(
            @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam(required = false, value = "itemId") Long itemId) {
        System.out.println(itemId);

        // 미니홈을 접속했는지 안했는지의 여부는 프론트에서 확인합니다.

        Long userId = customUserDetails.getAccountDto().getUserId();

        List<PlaylistDto> responseDto = playlistService.getAllplaylist(userId, itemId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // 사용자가 플레이 리스트 추가를 할 수 있어야됨.
    @Operation(summary = "나의 플레이리스트 추가")
    @SecurityRequirement(name = "Baerer Authentication")
    @PostMapping
    public ResponseEntity<?> addPlayList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody PlaylistDto playlistDto) {

        Long userId = customUserDetails.getAccountDto().getUserId();

        playlistService.addPlayList(userId, playlistDto);

        return new ResponseEntity<>("플레이리스트 생성 완료", HttpStatus.OK);
    }

    // 각 사용자의 플리를 추가/업데이트
    @Operation(summary = "나의 플레이리스트 수정")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "플리 수정이 완료되었습니다."),
    })
    @SecurityRequirement(name = "Baerer Authentication")
    @PutMapping(value = "/{playlistId}")
    public ResponseEntity<?> putMyPlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("playlistId") Long playlistId,
            @RequestBody PlaylistDto playlistDto)
            throws BadRequestException {

        Long userId = customUserDetails.getAccountDto().getUserId();

        playlistService.putMyPlaylist(playlistDto, playlistId, userId);

        return new ResponseEntity<>("수정완료", HttpStatus.OK);

    }

    @Operation(summary = "나의 플레이리스트 삭제")
    @SecurityRequirement(name = "Baerer Authentication")
    @DeleteMapping(value = "/{playlistId}")
    public ResponseEntity<?> deleteMyPlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("playlistId") Long playlistId) {

        Long userId = customUserDetails.getAccountDto().getUserId();

        playlistService.deleteMyPlaylist(playlistId, userId);

        return new ResponseEntity<>("삭제완료", HttpStatus.OK);

    }

    @Operation(summary = "특정 플레이리스트의 노래 삭제")
    @SecurityRequirement(name = "Baerer Authentication")
    @DeleteMapping("/{playlistId}/bgm/{buyItemId}")
    public ResponseEntity<?> deleteBgm(@PathVariable("playlistId") Long playlistId, @PathVariable("buyItemId") Long buyItemId) {
        bgmService.deleteBgm(playlistId, buyItemId);

        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

    @Operation(summary = "특정 플레이리스트의 특정 노래 추가")
    @SecurityRequirement(name = "Baerer Authentication")
    @PostMapping("/{playlistId}/bgm/{buyItemId}")
    public ResponseEntity<?> postBgmAdd(@PathVariable("playlistId") Long playlistId, @PathVariable("buyItemId") Long buyItemId) {

        bgmService.addBgm(playlistId, buyItemId);

        return new ResponseEntity<>("추가 완료", HttpStatus.OK);
    }

}
