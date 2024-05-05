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
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.BoardNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.service.MiniHomePostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "미니홈 게시글", description = "미니홈 게시글 API")
@RequestMapping("/api/minihome/post")
@RestController
public class MiniHomePostController {

    @Autowired
    MiniHomePostService miniHomePostService;

    @Operation(summary = "미니홈 게시글 번호로 내용 조회", description = "특정 게시글 내용 조회")
    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam Long postID) throws PostNotFoundException {

        MiniHomePostDto minihomePostDto = miniHomePostService.getPost(postID);

        return new ResponseEntity<>(minihomePostDto, HttpStatus.OK);
    }

    @Operation(summary = "미니홈 전체 게시글 리스트 조회", description = "미니홈 모든 게시글 내용 조회")
    @PostMapping
    public ResponseEntity<?> getPostList(@RequestBody PageRequestDto pageRequestDto) {
        PageResponseDto<MiniHomePostDto> responseDto = miniHomePostService.getPostList(pageRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @Operation(summary = "미니홈 게시글 번호로 게시글 삭제", description = "인증된 이용자의 자기 자신의 미니홈 게시글을 삭제")
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

    @Operation(summary = "미니홈 게시글 등록 / 수정", description = "인증된 사용자의 자기자신의 특정 게시글 등록 또는 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> putPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MiniHomePostDto miniHomePostDto) throws PermissionDeniedException, BoardNotFoundException {
        if (customUserDetails != null) {

            miniHomePostDto = MiniHomePostDto.builder()
                    .postID(miniHomePostDto.getUserID())
                    .userID(customUserDetails.getUserDto().getUserID())
                    .hpID(customUserDetails.getUserDto().getHpID())
                    .boardID(miniHomePostDto.getBoardID())
                    .title(miniHomePostDto.getTitle())
                    .content(miniHomePostDto.getContent())
                    .build();
            ApiResponse apiResponse = miniHomePostService.putPost(miniHomePostDto);

            return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
        }
        throw new PermissionDeniedException();
    }
}
