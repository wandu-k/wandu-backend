package com.example.wandukong.controller.general;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.service.user.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "유저", description = "유저 관련 공개 API")
@RequestMapping("/api/public/user")
@RestController
public class PublicUserController {

    @Autowired
    UserService userService;

    // 회원정보 조회
    @Operation(summary = "회원 조회", description = "회원 정보를 조회를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "422", description = "존재하지 않는 회원입니다."),
    })
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserInfo(@PathVariable("userId") Long userId,
            @RequestParam(required = false, value = "followCheckUserId") Long followCheckUserId) throws UserNotFoundException {

        log.info("유저 정보 조회 컨트롤러");
        UserDto userDto = userService.getUserInfo(userId, followCheckUserId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

}
