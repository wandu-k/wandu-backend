package com.example.wandukong.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.PictureDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.model.ApiResponseDto;

@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public void deletePicture(Long pictureId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePicture'");
    }

    @Override
    public PictureDto getPicture(Long pictureId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPicture'");
    }

    @Override
    public ApiResponseDto putPicture(MultipartFile image, PictureDto pictureDto) {

        if (pictureDto.getPictureId() == null) {

            ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                    .message("등록 완료").status(HttpStatus.OK).build();

            return apiResponseDto;

        }
        return null;
    }

    @Override
    public SliceResponseDto<PictureDto> getPictureList(Long userId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPictureList'");
    }

}
