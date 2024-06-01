package com.example.wandukong.controller.admin;

import com.example.wandukong.dto.forum.ForumPostDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BoardNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.forum.ForumPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "관리자 자유 게시글", description = "관리자 자유 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/forum/post")
public class AdminForumPostController {

    private final ForumPostService forumPostService;

    @Operation(summary = "관리자 자유게시판 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> modify(@RequestPart(required = false, value = "postFile") MultipartFile postFile,
                                    @RequestPart ForumPostDto forumPostDto)
            throws BoardNotFoundException, IOException {

        ApiResponseDto apiResponse = forumPostService.modify(postFile, forumPostDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @Operation(summary = "관리자 자유게시판 리스트 조회")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<ForumPostDto> postDto = forumPostService.adminGetList(pageRequestDto);

        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }
}
