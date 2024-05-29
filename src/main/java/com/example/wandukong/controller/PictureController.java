package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.PictureDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.PictureService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/picture")
public class PictureController {

    private final PictureService pictureService;

    @GetMapping
    public ResponseEntity<?> getPicture(@RequestParam Long pictureId) {

        PictureDto pictureDto = pictureService.getPicture(pictureId);
        return new ResponseEntity<>(pictureDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> getPictureList(@RequestParam Long userId, @RequestBody SliceRequestDto sliceRequestDto) {
        SliceResponseDto<PictureDto> pSliceResponseDto = pictureService.getPictureList(userId);
        return new ResponseEntity<>(pSliceResponseDto, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> putPicture(
            @RequestPart(required = true, value = "image") MultipartFile image,
            @RequestPart PictureDto pictureDto) {

        ApiResponseDto apiResponseDto = pictureService.putPicture(image, pictureDto);

        return new ResponseEntity<>(apiResponseDto.getMessage(), apiResponseDto.getStatus());
    }

    @DeleteMapping
    public ResponseEntity<?> deletePicture(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long pictureId, @RequestParam Long userId) throws BadRequestException {
        if (customUserDetails.getAccountDto().getUserId() != userId) {
            throw new BadRequestException();
        }
        pictureService.deletePicture(pictureId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
