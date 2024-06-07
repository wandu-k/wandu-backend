package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.AvatarDto;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.service.AvatarService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @PutMapping
    public ResponseEntity<?> putAvatar(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody AvatarDto avatarDto) {

        Long userId = customUserDetails.getAccountDto().getUserId();

        avatarService.putAvatar(userId, avatarDto);

        return new ResponseEntity<>("아바타 편집 완료", HttpStatus.OK);
    }

}
