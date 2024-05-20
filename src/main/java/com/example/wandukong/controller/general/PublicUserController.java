package com.example.wandukong.controller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "유저", description = "유저 관련 공개 API")
@RequestMapping("/api/public/user")
@RestController
public class PublicUserController {

    @Autowired
    AccountService accountService;

    // 회원정보 조회
    @Operation(summary = "회원 조회", description = "회원 정보를 조회를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "422", description = "존재하지 않는 회원입니다."),
    })
    @GetMapping
    public ResponseEntity<?> getUserInfo(@RequestParam Long userId) throws UserNotFoundException {

        UserDto userDto = accountService.getUserInfo(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
