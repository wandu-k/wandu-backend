package com.example.wandukong.controller.ask;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ask.AskDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.ask.AskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "공지사항 게시글", description = "공지사항 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/ask")
public class AskController {

    private final AskService askService;

    @Operation(summary = "공지사항 게시판 번호로 조회")
    @GetMapping
    public ResponseEntity<?> get(@RequestParam Long askId) throws PostNotFoundException {

        AskDto askDto = askService.get(askId);

        return new ResponseEntity<>(askDto, HttpStatus.OK);
    }

    @Operation(summary = "공시사항 게시판 리스트 조회")
    @PostMapping
    public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<AskDto> askDto = askService.getList(pageRequestDto);

        return new ResponseEntity<>(askDto, HttpStatus.OK);
    }

    @Operation(summary = "공지사항 게시판 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestBody AskDto askDto) throws BadRequestException {

        if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), askDto.getUserId())) {
            throw new BadRequestException();
        }

        ApiResponseDto apiResponse = askService.modify(askDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @Operation(summary = "공지사항 게시판 번호로 게시글 삭제")
    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long askId)
            throws PostNotFoundException, PermissionDeniedException {

        if (customUserDetails != null) {
            Long userId = customUserDetails.getAccountDto().getUserId();
            askService.remove(userId, askId);
            return new ResponseEntity<>("게시글 삭제가 완료되었습니다.", HttpStatus.OK);
        }

        throw new PermissionDeniedException();
    }
}
