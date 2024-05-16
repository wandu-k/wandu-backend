package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.FollowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "팔로우", description = "팔로우 API")
@RestController
@RequestMapping("/api/user/follow")
public class FollowController {

    @Autowired
    FollowService followService;

    @Operation(summary = "팔로잉 리스트", description = "유저 번호로 팔로잉 리스트를 가져옵니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/following")
    public ResponseEntity<?> getFollowingList(@RequestParam Long userId) {

        List<FriendDto> friends = followService.getFollowingList(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @Operation(summary = "팔로워 리스트", description = "유저 번호로 팔로워 리스트를 가져옵니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/follower")
    public ResponseEntity<?> getFollowerList(@RequestParam Long userId) {
        List<FriendDto> friends = followService.getFollowerList(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @Operation(summary = "팔로잉", description = "팔로잉")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> following(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendDto friendDto) throws BadRequestException {

        if (customUserDetails.getUserDto().getUserId() != friendDto.getUserId()) {
            throw new BadRequestException();
        }

        followService.following(friendDto);

        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

    @Operation(summary = "팔로잉 취소", description = "팔로잉 취소")
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping
    public ResponseEntity<?> unFollowing(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendDto friendDto) throws BadRequestException {

        if (customUserDetails.getUserDto().getUserId() != friendDto.getUserId()) {
            throw new BadRequestException();
        }

        followService.unFollowing(friendDto);

        return new ResponseEntity<>("팔로우 취소 성공", HttpStatus.OK);

    }

}
