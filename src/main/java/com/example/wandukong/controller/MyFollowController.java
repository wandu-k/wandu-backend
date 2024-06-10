package com.example.wandukong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.FollowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "팔로우", description = "내 팔로우/팔로워 관리 기능API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/follow")
public class MyFollowController {

    private final FollowService followService;

    @Operation(summary = "팔로잉", description = "팔로잉")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{userId}")
    public ResponseEntity<?> following(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId) throws BadRequestException {

        Long accountId = customUserDetails.getAccountDto().getUserId();

        followService.following(accountId, userId);

        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

    @Operation(summary = "팔로잉 취소", description = "팔로잉 취소")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> unFollowing(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long userId) throws BadRequestException {

        Long accountId = customUserDetails.getAccountDto().getUserId();

        followService.unFollowing(accountId, userId);

        return new ResponseEntity<>("팔로우 취소 성공", HttpStatus.OK);

    }

}
