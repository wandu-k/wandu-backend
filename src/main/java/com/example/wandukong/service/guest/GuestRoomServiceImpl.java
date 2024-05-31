package com.example.wandukong.service.guest;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.guest.GuestRoom;
import com.example.wandukong.dto.guest.GuestRoomDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.guest.GuestRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GuestRoomServiceImpl implements GuestRoomService {

    private final GuestRoomRepository guestRoomRepository;

    @Transactional
    @Override
    public ApiResponseDto modify(GuestRoomDto guestRoomDto) throws PostNotFoundException {
        Optional<GuestRoom> result = guestRoomRepository.findById(guestRoomDto.getRoomId());

        if (result.isPresent()) {
            result.get().changeContent(guestRoomDto.getMainContent());

            return ApiResponseDto.builder()
                    .message("방명록 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            GuestRoom guestRoom = GuestRoom.builder()
                    .userDo(UserDo.builder().userId(guestRoomDto.getUserId()).build())
                    .mainContent(guestRoomDto.getMainContent())
                    .writeDate(guestRoomDto.getWriteDate())
                    .build();
            guestRoomRepository.save(guestRoom);
            return ApiResponseDto.builder()
                    .message("방명록 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public PageResponseDto<GuestRoomDto> getList(PageRequestDto pageRequestDto) {
        Page<GuestRoom> result = guestRoomRepository.search(pageRequestDto);

        List<GuestRoom> guests = result.getContent();

        List<GuestRoomDto> dtoList = new ArrayList<>();
        for (GuestRoom guest : guests) {
            GuestRoomDto guestRoomDto = GuestRoomDto.builder()
                    .roomId(guest.getRoomId())
                    .userId(guest.getUserDo().getUserId())
                    .mainContent(guest.getMainContent())
                    .writeDate(guest.getWriteDate())
                    .build();
            dtoList.add(guestRoomDto);
        }

        return PageResponseDto.<GuestRoomDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .total(result.getTotalElements())
                .build();
    }

    @Override
    public void remove(Long userId, Long roomId) throws PostNotFoundException, PermissionDeniedException {
        GuestRoom guestRoom = guestRoomRepository.findById(roomId).orElseThrow(PostNotFoundException::new);

        if (!Objects.equals(guestRoom.getUserDo().getUserId(), userId)) {
            throw new PermissionDeniedException();
        }

        guestRoomRepository.deleteById(roomId);
    }
}
