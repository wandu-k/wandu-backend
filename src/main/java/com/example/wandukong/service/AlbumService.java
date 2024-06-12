package com.example.wandukong.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.AlbumDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.model.ApiResponseDto;

public interface AlbumService {

    void deletePicture(Long albumId);

    ApiResponseDto putPicture(MultipartFile image, AlbumDto pictureDto);

    List<AlbumDto> getAlbumList(Long userId);

    AlbumDto getAlbum(Long albumId);

    void addAlbum(MultipartFile multipartFile, Long userId, AlbumDto albumDto) throws IOException;

}
