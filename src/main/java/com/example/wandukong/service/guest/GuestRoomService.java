package com.example.wandukong.service.guest;

import com.example.wandukong.dto.guest.GuestCommentDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;

public interface GuestRoomService {

    ApiResponseDto modify(GuestCommentDto guestCommentDto) throws PostNotFoundException;

    PageResponseDto<GuestCommentDto> getList(Long hpId, PageRequestDto pageRequestDto);

    void remove(Long userId, Long commentId) throws PostNotFoundException, PermissionDeniedException;
}
