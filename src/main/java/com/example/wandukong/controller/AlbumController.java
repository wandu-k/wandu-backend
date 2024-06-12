package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.AlbumDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.service.AlbumService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class AlbumController {

    private final AlbumService albumService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{userId}/album/{albumId}")
    public ResponseEntity<?> getAlbum(@PathVariable("albumId") Long albumId) {

        AlbumDto pictureDto = albumService.getAlbum(albumId);
        return new ResponseEntity<>(pictureDto, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/{userId}/album/list")
    public ResponseEntity<?> getAlbumList(@PathVariable("userId") Long userId,
            @RequestBody(required = false) SliceRequestDto sliceRequestDto) {
        List<AlbumDto> albumDtos = albumService.getAlbumList(userId);
        return new ResponseEntity<>(albumDtos, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/album")
    public ResponseEntity<?> addAlbum(
        @RequestPart("multipartFile")  MultipartFile multipartFile,
            @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestPart("albumDto") AlbumDto albumDto)
            throws IOException {

        Long userId = customUserDetails.getAccountDto().getUserId();
        albumService.addAlbum(multipartFile, userId, albumDto);
        return new ResponseEntity<>("게시되었습니다.", HttpStatus.OK);
    }
    // @PutMapping
    // public ResponseEntity<?> putPicture(
    // @RequestPart(required = true, value = "image") MultipartFile image,
    // @RequestPart PictureDto pictureDto) {

    // ApiResponseDto apiResponseDto = pictureService.putPicture(image, pictureDto);

    // return new ResponseEntity<>(apiResponseDto.getMessage(),
    // apiResponseDto.getStatus());
    // }

    // @DeleteMapping
    // public ResponseEntity<?> deletePicture(@AuthenticationPrincipal
    // CustomUserDetails customUserDetails,
    // @RequestParam Long pictureId, @RequestParam Long userId) throws
    // BadRequestException {
    // if (customUserDetails.getAccountDto().getUserId() != userId) {
    // throw new BadRequestException();
    // }
    // pictureService.deletePicture(pictureId);
    // return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    // }
}
