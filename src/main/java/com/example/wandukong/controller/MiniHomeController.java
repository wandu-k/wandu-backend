package com.example.wandukong.controller;

import com.example.wandukong.exception.CustomException;
import org.springframework.web.bind.annotation.*;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.service.MiniHomeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RequestMapping("/api/user")
@RestController
public class MiniHomeController {

    @Autowired
    MiniHomeService miniHomeService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/minihome")
    public ResponseEntity<?> getMiniHome(@PathVariable("userId") Long userId, @RequestParam Long likeUserId) throws HomeNotFoundException {

        MiniHomeDto miniHomeDto = miniHomeService.getMiniHome(userId, likeUserId);
        return new ResponseEntity<>(miniHomeDto, HttpStatus.OK);
    }

    @Operation(summary = "미니홈 플레이리스트 설정")
    @PatchMapping("/{userId}/minihome/{hpId}/playlist")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> setMiniHomePlaylist(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                 @PathVariable("userId") Long userId,
                                                 @PathVariable("hpId") Long hpId,
                                                 @RequestBody(required = false) Long playlistId) throws HomeNotFoundException, CustomException.BadRequestException {

        if (userId != customUserDetails.getAccountDto().getUserId()) {
            throw new CustomException.BadRequestException();
        }
        miniHomeService.setMiniHomePlaylist(hpId, playlistId);
        return new ResponseEntity<>("미니홈 플레이리스트 설정 완료", HttpStatus.OK);
    }

    @Operation(summary = "미니홈 설정")
    @PatchMapping("/{userId}/minihome/{hpId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> setMiniHome(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("userId") Long userId,
                                         @PathVariable("hpId") Long hpId,
                                         @RequestBody MiniHomeDto miniHomeDto) throws CustomException.BadRequestException {

        if (userId != customUserDetails.getAccountDto().getUserId()) {
            throw new CustomException.BadRequestException();
        }

        miniHomeService.setMiniHome(hpId, miniHomeDto);
        return new ResponseEntity<>("미니홈 설정 완료", HttpStatus.OK);
    }

}
