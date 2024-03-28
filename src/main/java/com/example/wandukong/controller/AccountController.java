package com.example.wandukong.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "JWT 인증", description = "사용자를 JWT로 인증합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "JWT 생성 성공"),
            @ApiResponse(responseCode = "401", description = "인증 실패")
    })
    @PostMapping("/login")
    public void authenticateJwt(@RequestParam String username, @RequestParam String password,
            HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 이 메서드는 실제로는 필터에서 수행되므로 컨트롤러의 내용은 비워두어도 됩니다.
        // Swagger 문서에는 필요한 설명과 응답 코드만 추가하면 됩니다.
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().println("JWT 생성 성공");
    }

    // 회원가입

    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입이 완료되었습니다!"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {
        int result = accountService.register(userDto);

        if (result == 0) {
            return new ResponseEntity<>("회원가입이 완료되었습니다!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
    }

    // 내 정보 조회
    @Operation(summary = "내 정보 조회", description = "자기자신의 회원 정보를 조회를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴가 완료되었습니다."),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
    })
    @GetMapping("/myinfo")
    public ResponseEntity<?> myinfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        if (customUserDetails != null) {
            UserDto userDto = customUserDetails.getUserDto();
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        }

        return new ResponseEntity<>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
    }

    @Operation(summary = "회원탈퇴", description = "인증된 사용자의 회원 탈퇴를 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원탈퇴가 완료되었습니다."),
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
    })
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete")
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
            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다."),
            @ApiResponse(responseCode = "401", description = "지원하지 않는 파일 타입")
    })
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestPart(required = false) @Parameter(description = "프로필 사진") MultipartFile profileImage,
            @Parameter(description = "업데이트할 사용자 정보") @RequestPart UserDto userDto) {

        if (customUserDetails != null) {
            return new ResponseEntity<>("프로필 업데이트가 완료 되었습니다.", HttpStatus.OK);
        }
        return new ResponseEntity<>("인증되지 않은 사용자입니다.", HttpStatus.UNAUTHORIZED);
    }

}
