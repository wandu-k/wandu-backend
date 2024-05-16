package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.FollowService;

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

@RestController
@RequestMapping("/api/user/follow")
public class FollowController {

    @Autowired
    FollowService followService;

    @GetMapping("/following")
    public ResponseEntity<?> getFollowingList(@RequestParam Long userId) {

        List<FriendDto> friends = followService.getFollowingList(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @GetMapping("/follower")
    public ResponseEntity<?> getFollowerList(@RequestParam Long userId) {
        List<FriendDto> friends = followService.getFollowerList(userId);

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> following(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FriendDto friendDto) throws BadRequestException {

        if (customUserDetails.getUserDto().getUserId() != friendDto.getUserId()) {
            throw new BadRequestException();
        }

        followService.following(friendDto);

        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

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
