package com.example.wandukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.service.AccountService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequestMapping("/api/user")
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        int result = accountService.register(userDto);

        if (result == 0) {
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
        }
    }

    // 내 정보 조회
    @GetMapping("/myinfo")
    public ResponseEntity<?> myinfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        UserDto userDto = customUserDetails.getUserDto();
        log.info("user : " + userDto);
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        UserDto userDto = customUserDetails.getUserDto();
        if (userDto != null) {
            accountService.deleteAccount(userDto.getUserID());
            return new ResponseEntity<>("회원탈퇴가 완료되었습니다.", HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfilePicture(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MultipartFile multipartFile) {

        if (customUserDetails != null) {
            return new ResponseEntity<>("프로필 업데이트가 완료 되었습니다.", HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);

    }

}
