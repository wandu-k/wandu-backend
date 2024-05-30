package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.DailyDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.DailyCheckService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/daily")
public class DailyCheckController {

    final private DailyCheckService dailyCheckService;

    @PostMapping
    public ResponseEntity<?> dailyCheck(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody DailyDto dailyDto) throws BadRequestException {
        if (customUserDetails.getAccountDto().getUserId() != dailyDto.getUserId()) {
            throw new BadRequestException();
        }
        dailyCheckService.dailyCheck(dailyDto);
        return new ResponseEntity<>("출석체크 완료", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getdailyCheckList(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate date) {

        List<DailyDto> dailyDto = dailyCheckService.getdailyCheckList(date);
        return new ResponseEntity<>(dailyDto, HttpStatus.OK);
    }

}
