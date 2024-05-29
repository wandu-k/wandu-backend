package com.example.wandukong.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.PictureDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.model.ApiResponseDto;

public interface PictureService {

    void deletePicture(Long pictureId);

    PictureDto getPicture(Long pictureId);

    ApiResponseDto putPicture(MultipartFile image, PictureDto pictureDto);

    SliceResponseDto<PictureDto> getPictureList(Long userId);

}
