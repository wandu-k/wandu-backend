package com.example.wandukong.service.guest;

import com.example.wandukong.dto.guest.GuestRoomDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.guest.GuestRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestRoomServiceImpl implements GuestRoomService {

    private final GuestRoomRepository guestRoomRepository;

    @Override
    public ApiResponseDto modify(GuestRoomDto guestRoomDto) throws PostNotFoundException {

        return null;
    }

    @Override
    public PageResponseDto<GuestRoomDto> getList(PageRequestDto pageRequestDto) {
        return null;
    }

    @Override
    public void remove(Long userId, Long commentId) throws PostNotFoundException, CustomException.PermissionDeniedException {

    }
}
