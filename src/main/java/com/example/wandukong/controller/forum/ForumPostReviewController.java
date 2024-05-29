package com.example.wandukong.controller.forum;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.forum.ForumPostReviewDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.forum.ForumPostReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "자유 게시글 댓글", description = "자유 게시글 댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/forum/post/review")
public class ForumPostReviewController {

    private final ForumPostReviewService forumPostReviewService;

    @Operation(summary = "특정 자유게시판에 해당하는 댓글 조회")
    @PostMapping
    public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<ForumPostReviewDto> reviewDto = forumPostReviewService.getList(pageRequestDto);

        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @Operation(summary = "자유게시판 댓글 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestBody ForumPostReviewDto forumPostReviewDto) throws CustomException.PostNotFoundException, CustomException.BadRequestException {
        if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), forumPostReviewDto.getUserId())) {
            throw new CustomException.BadRequestException();
        }

        ApiResponseDto apiResponse = forumPostReviewService.modify(forumPostReviewDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @Operation(summary = "자유 게시판 댓글 삭제")
    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long commentId) throws CustomException.PostNotFoundException, CustomException.PermissionDeniedException {

        if (customUserDetails != null) {
            Long userId = customUserDetails.getAccountDto().getUserId();
            forumPostReviewService.remove(userId, commentId);
            return new ResponseEntity<>("댓글 삭제가 완료되었습니다.", HttpStatus.OK);
        }

        throw new CustomException.PermissionDeniedException();
    }
}
