package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.exception.CustomException.HomeNotFoundException;
import com.example.wandukong.security.CustomUserDetailService;
import com.example.wandukong.service.MiniHomePostCommentService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/minihome/post/comment")
public class MiniHomePostCommentController {

    private final MiniHomePostCommentService minihomePostCommentService;

    public ResponseEntity<?> getComment(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long postId) throws HomeNotFoundException {

        Long userId = customUserDetails.getUserDto().getUserId();

        MiniHomeDto miniHomeDto = minihomePostCommentService.getComment(postId, userId);
        return new ResponseEntity<>(miniHomeDto, HttpStatus.OK);
    };

}
