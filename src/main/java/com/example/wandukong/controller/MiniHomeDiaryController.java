package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.service.diary.DiaryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/diary")
public class MiniHomeDiaryController {

    private final DiaryService miniHomeDiaryService;

    @GetMapping
    public ResponseEntity<?> getPost(@RequestParam Long postId) {
        log.info("다이어리 get 진입");

        DiaryDto diaryDto = miniHomeDiaryService.getPost(postId);

        return new ResponseEntity<>(diaryDto, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> getList(@RequestBody SearchDiaryDto searchDiaryDto) {
        log.info("다이어리 Post 진입");
        List<DiaryDto> list = miniHomeDiaryService.getList(searchDiaryDto);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> putPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody DiaryDto miniHomeDiaryDto) throws BadRequestException {

        log.info("다이어리 Put 진입");

        if (customUserDetails.getAccountDto().getUserId() != miniHomeDiaryDto.getUserId()) {
            throw new BadRequestException();
        }

        ApiResponse apiResponse = miniHomeDiaryService.putPost(miniHomeDiaryDto);
        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

}
