package com.example.wandukong.service.ShopInfo;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;

public interface PlaylistService {

  SliceResponseDto<PlaylistAllDto> getAllplaylist(SliceRequestDto sliceRequestDto, UserDto userDto);

}
