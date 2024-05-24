package com.example.wandukong.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.service.MiniHomePostService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "미니홈 게시글", description = "미니홈 게시글 API")
@RequestMapping("/api/admin/minihome/post")
@RequiredArgsConstructor
@RestController
public class AdminMiniHomePostController {

    private final MiniHomePostService miniHomePostService;

    @Operation(summary = "미니홈 게시글 번호로 내용 조회", description = "특정 게시글 내용 조회")
    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam Long postId) throws PostNotFoundException {

        MiniHomePostDto minihomePostDto = miniHomePostService.getPost(postId);

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
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MiniHomePostDto miniHomePostDto)
            throws PostNotFoundException {
        log.info("Admin Delete 컨트롤러");
        // adminMiniHomePostService.deletePost(miniHomePostDto);
        return new ResponseEntity<>("게시글 삭제가 완료되었습니다.", HttpStatus.OK);
    }
}
