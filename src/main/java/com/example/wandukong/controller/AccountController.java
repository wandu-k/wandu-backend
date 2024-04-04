package com.example.wandukong.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.exception.CustomException.UserAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.security.jwt.JwtToken;
import com.example.wandukong.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "계정", description = "계정 API")
@RequestMapping("/api/user")
@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @Operation(summary = "JWT 인증", description = "이용자를 JWT로 인증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT 생성 성공"),
            @ApiResponse(responseCode = "401", description = "회원 인증 실패")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        JwtToken token = accountService.login(username, password);
        return ResponseEntity.ok(token);

    }

    // 회원가입

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 완료되었습니다!"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) throws UserAlreadyExistsException {
        accountService.register(userDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다!", HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    // 내 정보 조회
    @Operation(summary = "내 정보 조회", description = "자기자신의 회원 정보를 조회를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 이용자입니다."),
    })
    @GetMapping("/myinfo")
    public ResponseEntity<?> getMyInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails != null) {
            UserDto userDto = accountService.getMyInfo(customUserDetails.getUsername());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    // 다른 사람 정보 조회
    @Operation(summary = "회원 정보 조회", description = "다른 회원 정보를 조회를 합니다.")
    @ApiResponse(responseCode = "422", description = "해당하는 회원이 없습니다.")
    @GetMapping("{userID}")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userID) throws UserNotFoundException {

        UserDto userDto = accountService.getUserInfo(userID);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    @Operation(summary = "회원탈퇴", description = "인증된 사용자의 회원 탈퇴를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴가 완료되었습니다."),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 이용자입니다."),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails != null) {
            UserDto userDto = customUserDetails.getUserDto();
            accountService.deleteAccount(userDto.getUserID());
            return new ResponseEntity<>("회원탈퇴가 완료되었습니다.", HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "프로필 업데이트", description = "인증된 사용자의 프로필을 업데이트합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "프로필 업데이트가 완료 되었습니다."),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 이용자입니다."),
            @ApiResponse(responseCode = "422", description = "해당하는 회원이 없습니다.")
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart(required = false, value = "profileImage") MultipartFile profileImage,
            @RequestPart(value = "userDto") @Parameter(schema = @Schema(type = "string", format = "binary")) UserDto userDto)
            throws IOException, UserNotFoundException {
        if (customUserDetails != null) {
            userDto.setUserID(customUserDetails.getUserDto().getUserID());

            accountService.updateProfile(profileImage, userDto);
            return new ResponseEntity<>("프로필 업데이트가 완료 되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("인증되지 않은 이용자입니다.", HttpStatus.UNAUTHORIZED);
    }

}
