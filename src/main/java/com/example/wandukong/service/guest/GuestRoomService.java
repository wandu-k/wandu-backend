package com.example.wandukong.service.guest;

import com.example.wandukong.dto.guest.GuestRoomDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;

public interface GuestRoomService {

    ApiResponseDto modify(GuestRoomDto guestRoomDto) throws PostNotFoundException;

    PageResponseDto<GuestRoomDto> getList(PageRequestDto pageRequestDto);

    void remove(Long userId, Long commentId) throws PostNotFoundException, PermissionDeniedException;
}
