package com.example.wandukong.controller.ask;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ask.AdminCommentDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.ask.AdminCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "공지사항 댓글", description = "공지사항 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/ask/comment")
public class AdminCommentController {

    private final AdminCommentService adminCommentService;

    @Operation(summary = "특정 공지사항에 해당하는 댓글 조회")
    @PostMapping
    public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<AdminCommentDto> commentDto = adminCommentService.getList(pageRequestDto);

        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @Operation(summary = "공지사항 댓글 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestBody AdminCommentDto adminCommentDto) throws PostNotFoundException, BadRequestException {
        if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), adminCommentDto.getUserId())) {
            throw new BadRequestException();
        }

        ApiResponseDto apiResponseDto = adminCommentService.modify(adminCommentDto);

        return new ResponseEntity<>(apiResponseDto.getMessage(), apiResponseDto.getStatus());
    }

    @Operation(summary = "공지사항 댓글 삭제")
    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long commentId) throws PostNotFoundException, PermissionDeniedException {

        if (customUserDetails != null) {
            Long userId = customUserDetails.getAccountDto().getUserId();
            adminCommentService.remove(userId, commentId);
            return new ResponseEntity<>("댓글 삭제가 완료되었습니다.", HttpStatus.OK);
        }

        throw new PermissionDeniedException();
    }
}
