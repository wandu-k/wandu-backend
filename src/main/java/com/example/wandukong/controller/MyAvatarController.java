package com.example.wandukong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.RequestAvatarDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.AvatarService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/my/avatar")
@RequiredArgsConstructor
public class MyAvatarController {

    private final AvatarService avatarService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{buyItemId}")
    public ResponseEntity<?> putAvatar(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable("buyItemId") Long buyItemId,
            @RequestBody RequestAvatarDto requestAvatarDto) throws BadRequestException {

        Long userId = customUserDetails.getAccountDto().getUserId();
        avatarService.putAvatar(userId, buyItemId, requestAvatarDto);

        return new ResponseEntity<>("아바타 편집 완료", HttpStatus.OK);
    }
}
