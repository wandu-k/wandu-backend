package com.example.wandukong.service.ShopInfo;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.model.ApiResponseDto;

public interface ShopService {

        ApiResponseDto putItem(MultipartFile itemfile, ShopDto shopDto) throws IOException, BadRequestException;

        PageResponseDto<ShopInfoDto> getShopItemList(PageRequestDto pageRequestDto, SearchItemDto searchDiaryDto);
}
