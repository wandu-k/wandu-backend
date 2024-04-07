package com.example.wandukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.service.MiniHomePostService;

import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/minihome/post")
@RestController
public class MiniHomePostController {

    @Autowired
    MiniHomePostService miniHomePostService;

    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam Long postID) throws PostNotFoundException {

        MiniHomePostDto minihomePostDto = miniHomePostService.getPost(postID);

        return new ResponseEntity<>(minihomePostDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long postID)
            throws PostNotFoundException, PermissionDeniedException {
        Long userID = customUserDetails.getUserDto().getUserID();
        miniHomePostService.deletePost(userID, postID);
        return new ResponseEntity<>("게시글 삭제가 완료되었습니다.", HttpStatus.OK);

    }

}
