package com.example.wandukong.controller;

import java.util.Map;
import java.util.Objects;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.model.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.service.ForumPostService;

import lombok.RequiredArgsConstructor;

import static com.example.wandukong.exception.CustomException.*;

@Tag(name = "자유 게시글", description = "자유 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/forum/post")
public class ForumPostController {

  private final ForumPostService forumPostService;

  @Operation(summary = "자유게시판 번호로 조회")
  @GetMapping
  public ResponseEntity<?> get(@RequestParam Long postId) throws PostNotFoundException {

    ForumPostDto forumPostDto = forumPostService.get(postId);

    return new ResponseEntity<>(forumPostDto, HttpStatus.OK);
  }

  @Operation(summary = "자유게시판 리스트 조회")
  @PostMapping
  public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

    PageResponseDto<ForumPostDto> postDto = forumPostService.getList(pageRequestDto);

    return new ResponseEntity<>(postDto, HttpStatus.OK);
  }

  @Operation(summary = "자유게시판 등록 및 수정")
  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping
  public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestBody ForumPostDto forumPostDto)
          throws PermissionDeniedException, BoardNotFoundException, BadRequestException {

    if (!Objects.equals(customUserDetails.getUserDto().getUserId(), forumPostDto.getUserId())) {
      throw new BadRequestException();
    }

    ApiResponse apiResponse = forumPostService.modify(forumPostDto);

    return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
  }

  @Operation(summary = "자유 게시판 번호로 게시글 삭제")
  @DeleteMapping
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long postId)
          throws PostNotFoundException, PermissionDeniedException{

    if (customUserDetails != null) {
      Long userId = customUserDetails.getUserDto().getUserId();
      forumPostService.remove(userId, postId);
      return new ResponseEntity<>("게시글 삭제가 완료되었씁니다.", HttpStatus.OK);
    }

    throw new PermissionDeniedException();
  }
}
