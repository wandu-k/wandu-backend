package com.example.wandukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.UserAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증", description = "인증 API")
@RestController
@RequestMapping("/api/public")
public class AuthController {

    @Autowired
    AccountService accountService;

    @Operation(summary = "회원가입", description = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 완료되었습니다!"),
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/register")
    public ResponseEntity<?> register(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart(required = false, value = "profileImage") MultipartFile profileImage,
            @RequestPart(value = "userDto") @Parameter(schema = @Schema(type = "string", format = "binary")) UserDto userDto)
            throws UserNotFoundException, UserAlreadyExistsException, java.io.IOException {
        accountService.register(profileImage, userDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다!", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody String username, @RequestBody String password) {

        return new ResponseEntity<>("로그인 성공", HttpStatus.OK);
    }

}
