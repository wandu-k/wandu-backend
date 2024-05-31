package com.example.wandukong.controller.guest;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.guest.GuestRoomDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.guest.GuestRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "방명록", description = "방명록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/guest")
public class GuestRoomController {

    private final GuestRoomService guestRoomService;

    @Operation(summary = "방명록 조회")
    @PostMapping
    public ResponseEntity<?> list(@RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<GuestRoomDto> guestDto = guestRoomService.getList(pageRequestDto);

        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

    @Operation(summary = "방명록 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping
    public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestBody GuestRoomDto guestRoomDto) throws PostNotFoundException, BadRequestException {

        if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), guestRoomDto.getUserId())) {
            throw new BadRequestException();
        }

        ApiResponseDto apiResponseDto = guestRoomService.modify(guestRoomDto);

        return new ResponseEntity<>(apiResponseDto.getMessage(), apiResponseDto.getStatus());
    }

    @Operation(summary = "방명록 삭제")
    @DeleteMapping
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, Long roomId) throws PostNotFoundException, PermissionDeniedException {

        if (customUserDetails != null) {
            Long userId = customUserDetails.getAccountDto().getUserId();
            guestRoomService.remove(userId, roomId);
            return new ResponseEntity<>("방명록 삭제가 완료되었습니다.", HttpStatus.OK);
        }

        throw new PermissionDeniedException();
    }
}
