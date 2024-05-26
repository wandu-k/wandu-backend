package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.MiniHome.MiniHomeDiaryDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.service.MiniHomeDiaryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
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
@RequestMapping("/api/user/minihome/diary")
public class MiniHomeDiaryController {

    private final MiniHomeDiaryService miniHomeDiaryService;

    @GetMapping
    public String getMethodName(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return new String();
    }

    @PutMapping
    public ResponseEntity<?> putPost(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MiniHomeDiaryDto miniHomeDiaryDto) throws BadRequestException {

        log.info("다이어리 Put 진입");

        if (customUserDetails.getAccountDto().getUserId() != miniHomeDiaryDto.getUserId()) {
            throw new BadRequestException();
        }

        ApiResponse apiResponse = miniHomeDiaryService.putPost(miniHomeDiaryDto);
        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @PostMapping
    public String postMethodName(@RequestBody String entity) {
        // TODO: process POST request

        return entity;
    }

}
