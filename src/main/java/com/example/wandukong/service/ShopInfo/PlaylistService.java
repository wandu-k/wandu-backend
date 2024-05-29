package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.BgmListNotFoundException;
import com.example.wandukong.model.ApiResponseDto;

public interface PlaylistService {

  SliceResponseDto<PlaylistAllDto> getAllplaylist(SliceRequestDto sliceRequestDto, Long userId);

  ApiResponseDto putMyPlaylist(PlaylistDto playlistDto, BuyItemDto buyItemDto)
      throws BgmListNotFoundException, BadRequestException;

}
