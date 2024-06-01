package com.example.wandukong.controller.forum;

import java.io.IOException;
import java.util.Objects;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.model.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.wandukong.dto.forum.ForumPostDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.BoardNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.service.forum.ForumPostService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "자유 게시글", description = "자유 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/forum/post")
public class ForumPostController {

  private final ForumPostService forumPostService;

  @Operation(summary = "자유게시판 번호로 조회")
  @SecurityRequirement(name = "Baerer Authentication")
  @GetMapping
  public ResponseEntity<?> get(@RequestParam Long postId) throws PostNotFoundException {

    ForumPostDto forumPostDto = forumPostService.get(postId);

    return new ResponseEntity<>(forumPostDto, HttpStatus.OK);
  }

  @Operation(summary = "자유게시판 리스트 조회")
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping
  public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

    PageResponseDto<ForumPostDto> postDto = forumPostService.getList(pageRequestDto);

    return new ResponseEntity<>(postDto, HttpStatus.OK);
  }

  @Operation(summary = "자유게시판 등록 및 수정")
  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestPart(required = false, value = "postFile") MultipartFile postFile,
      @RequestPart ForumPostDto forumPostDto)
      throws BoardNotFoundException, BadRequestException, IOException {

    if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), forumPostDto.getUserId())) {
      throw new BadRequestException();
    }

    ApiResponseDto apiResponse = forumPostService.modify(postFile, forumPostDto);

    return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
  }

  @Operation(summary = "자유 게시판 번호로 게시글 삭제")
  @DeleteMapping
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long postId)
      throws PostNotFoundException, PermissionDeniedException {

    if (customUserDetails != null) {
      Long userId = customUserDetails.getAccountDto().getUserId();
      forumPostService.remove(userId, postId);
      return new ResponseEntity<>("게시글 삭제가 완료되었습니다.", HttpStatus.OK);
    }

    throw new PermissionDeniedException();
  }
}
