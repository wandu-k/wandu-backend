package com.example.wandukong.service.guest;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.guest.GuestComment;
import com.example.wandukong.dto.guest.GuestCommentDto;
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
    public ApiResponseDto modify(GuestCommentDto guestCommentDto) throws PostNotFoundException {
        Optional<GuestComment> result = guestRoomRepository.findById(guestCommentDto.getCommentId());

        if (result.isPresent()) {
            result.get().changeContent(guestCommentDto.getMainContent());

            return ApiResponseDto.builder()
                    .message("방명록 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            GuestComment guestComment = GuestComment.builder()
                    .userDo(UserDo.builder().userId(guestCommentDto.getUserId()).build())
                    .mainContent(guestCommentDto.getMainContent())
                    .writeDate(guestCommentDto.getWriteDate())
                    .build();
            guestRoomRepository.save(guestComment);
            return ApiResponseDto.builder()
                    .message("방명록 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public PageResponseDto<GuestCommentDto> getList(Long hpId, PageRequestDto pageRequestDto) {
        Page<GuestComment> result = guestRoomRepository.search(hpId, pageRequestDto);

        List<GuestComment> guests = result.getContent();

        List<GuestCommentDto> dtoList = new ArrayList<>();
        for (GuestComment guest : guests) {
            GuestCommentDto guestCommentDto = GuestCommentDto.builder()
                    .commentId(guest.getCommentId())
                    .hpId(guest.getMiniHome().getHpId())
                    .userId(guest.getUserDo().getUserId())
                    .mainContent(guest.getMainContent())
                    .writeDate(guest.getWriteDate())
                    .build();
            dtoList.add(guestCommentDto);
        }

        return PageResponseDto.<GuestCommentDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .total(result.getTotalElements())
                .build();
    }

    @Override
    public void remove(Long userId, Long roomId) throws PostNotFoundException, PermissionDeniedException {
        GuestComment guestComment = guestRoomRepository.findById(roomId).orElseThrow(PostNotFoundException::new);

        if (!Objects.equals(guestComment.getUserDo().getUserId(), userId)) {
            throw new PermissionDeniedException();
        }

        guestRoomRepository.deleteById(roomId);
    }
}
