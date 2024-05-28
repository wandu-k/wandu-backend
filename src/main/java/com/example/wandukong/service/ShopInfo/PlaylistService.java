package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.exception.CustomException.BgmListNotFoundException;
import com.example.wandukong.model.ApiResponse;

public interface PlaylistService {

  SliceResponseDto<PlaylistAllDto> getAllplaylist(SliceRequestDto sliceRequestDto, Long userId);

  ApiResponse updateMyPlaylist(PlaylistAllDto playlistAllDto) throws BgmListNotFoundException;

}
