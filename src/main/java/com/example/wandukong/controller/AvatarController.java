package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.RequestAvatarDto;
import com.example.wandukong.dto.ResponseAvatarDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.service.AvatarService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getAvatar(@PathVariable Long userId) {

        ResponseAvatarDto avatarDto = avatarService.getAvatar(userId);
        return new ResponseEntity<>(avatarDto, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> putAvatar(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RequestAvatarDto requestAvatarDto) throws BadRequestException {

        Long userId = customUserDetails.getAccountDto().getUserId();
        avatarService.putAvatar(userId, requestAvatarDto);

        return new ResponseEntity<>("아바타 편집 완료", HttpStatus.OK);
    }

}
