package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.MyStatisticsDto;
import com.example.wandukong.service.AccountService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my/statistics")
public class MyStatisticsController {

    private final AccountService accountService;

    // 내 통계
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping
    public ResponseEntity<?> getMyStatistics(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long userId = customUserDetails.getAccountDto().getUserId();

        MyStatisticsDto myStatisticsDto = accountService.getMyStatistics(userId);

        return new ResponseEntity<>(myStatisticsDto, HttpStatus.OK);
    }

}
