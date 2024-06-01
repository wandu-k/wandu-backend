package com.example.wandukong.controller.admin;

import com.example.wandukong.dto.ask.AskDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.ask.AskService;
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

@Tag(name = "관리자 공지사항 게시글", description = "관리자 공지사항 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/ask")
public class AdminAskController {

    private final AskService askService;

    @Operation(summary = "관리자 공지사항 게시판 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> modify(@RequestPart(required = false, value = "askFile") MultipartFile askFile,
                                    @RequestPart AskDto askDto) throws IOException {

        ApiResponseDto apiResponse = askService.modify(askFile, askDto);

        return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());
    }

    @Operation(summary = "관리자 공시사항 게시판 리스트 조회")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping
    public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<AskDto> askDto = askService.AdminGetList(pageRequestDto);

        return new ResponseEntity<>(askDto, HttpStatus.OK);
    }
}


