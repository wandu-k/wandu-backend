package com.example.demo.controller;

import com.example.demo.dto.CustomUserDetails;
import com.example.demo.dto.UserDto;
import com.example.demo.service.AccountService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    // 회원가입
    @PostMapping("/api/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        int result = accountService.register(userDto);

        if (result == 0) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 내 정보 조회
    @GetMapping("/api/myinfo")
    public ResponseEntity<?> myinfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        UserDto userDto = customUserDetails.getUserDto();
        log.info("user : " + userDto);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

}
