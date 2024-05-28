package com.example.wandukong.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.exception.CustomException.itemUploadNotFoundException;

public interface ShopService {

        PageResponseDto<ShopInfoDto> getShopitemList(PageRequestDto pageRequestDto);

        void putPost(MultipartFile itemfile, ShopDto shopDto)
                        throws itemUploadNotFoundException, IOException;

        void updateItemFile(MultipartFile itemfile, ShopInfoDto shopInfoDto)
                        throws itemUploadNotFoundException, IOException;

        PageResponseDto<ShopInfoDto> getMyitemUploadList(PageRequestDto pageRequestDto,
                        AccountDto accountDto) throws UserNotFoundException;
}
