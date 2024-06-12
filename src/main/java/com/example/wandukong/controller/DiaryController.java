package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.service.diary.DiaryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class DiaryController {

    private final DiaryService miniHomeDiaryService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/diary/{postId}")
    public ResponseEntity<?> getPost(@PathVariable Long userId, @PathVariable Long postId) {

        log.info("다이어리 get 단건 조회 컨트롤러 진입");

        System.out.println(userId + "" + postId);

        DiaryDto diaryDto = miniHomeDiaryService.getPost(userId, postId);

        return new ResponseEntity<>(diaryDto, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/diary/list")
    public ResponseEntity<?> getList(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) LocalDate date) {

        SearchDiaryDto searchDiaryDto = new SearchDiaryDto(date, null, userId);

        log.info("다이어리 get 리스트 조회 컨트롤러 진입");

        List<DiaryDto> list = miniHomeDiaryService.getList(searchDiaryDto);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/diary")
    public ResponseEntity<?> addPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody DiaryDto diaryDto) throws BadRequestException {
        log.info("다이어리 Post 진입");
        if (!customUserDetails.getAccountDto().getUserId().equals(diaryDto.getUserId())) {
            throw new BadRequestException();
        }
        miniHomeDiaryService.addPost(diaryDto);
        return new ResponseEntity<>("게시글 등록 완료", HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/diary/{postId}")
    public ResponseEntity<?> putPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long postId, @RequestBody DiaryDto diaryDto)
            throws BadRequestException, PostNotFoundException {

        log.info("다이어리 Put 진입");

        if (!customUserDetails.getAccountDto().getUserId().equals(diaryDto.getUserId())) {
            throw new BadRequestException();
        }
        miniHomeDiaryService.putPost(postId, diaryDto);
        return new ResponseEntity<>("수정이 완료되었습니다.", HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/diary/{postId}")
    public ResponseEntity<?> deletePost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long postId)
            throws BadRequestException, PostNotFoundException {

        log.info("다이어리 Put 진입");
        Long userId = customUserDetails.getAccountDto().getUserId();
        miniHomeDiaryService.deletePost(postId);
        return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
    }
}
