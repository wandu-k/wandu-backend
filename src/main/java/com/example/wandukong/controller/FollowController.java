package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.service.FollowService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "팔로우", description = "팔로우 기능API")
@RestController
@RequestMapping("/api/user")
public class FollowController {

    @Autowired
    FollowService followService;

    @Operation(summary = "팔로잉 리스트", description = "유저 번호로 팔로잉 리스트를 가져옵니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/follow/following")
    public ResponseEntity<?> getFollowingList(@PathVariable("userId") Long userId) {

        List<FriendDto> friends = followService.getFollowingList(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @Operation(summary = "팔로워 리스트", description = "유저 번호로 팔로워 리스트를 가져옵니다.")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/follow/follower")
    public ResponseEntity<?> getFollowerList(@PathVariable("userId") Long userId) {
        List<FriendDto> friends = followService.getFollowerList(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

}
