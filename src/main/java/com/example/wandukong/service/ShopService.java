package com.example.wandukong.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.itemUploadNotFoundException;

public interface ShopService {

    List<ShopInfoDto> getShopitemList();

    String putPost(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
            throws itemUploadNotFoundException;

    void updateItemFile(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
            throws itemUploadNotFoundException;
}
