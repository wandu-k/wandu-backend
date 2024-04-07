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
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.service.MiniHomePostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long postID)
            throws PostNotFoundException, PermissionDeniedException {
        if (customUserDetails != null) {
            Long userID = customUserDetails.getUserDto().getUserID();
            miniHomePostService.deletePost(userID, postID);
            return new ResponseEntity<>("게시글 삭제가 완료되었습니다.", HttpStatus.OK);
        }
        throw new PermissionDeniedException();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> putPost(@RequestBody MiniHomePostDto miniHomePostDto) {

        ApiResponse apiResponse = miniHomePostService.putPost(miniHomePostDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

}
