package com.example.wandukong.controller.guest;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.guest.GuestCommentDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.guest.GuestRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Tag(name = "방명록", description = "방명록 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class GuestRoomController {

    private final GuestRoomService guestRoomService;

    @Operation(summary = "방명록 조회")
    @PostMapping("/minihome/{hpId}/guest/list")
    public ResponseEntity<?> list(@PathVariable("hpId") Long hpId, @RequestBody PageRequestDto pageRequestDto) {

        PageResponseDto<GuestCommentDto> guestDto = guestRoomService.getList(hpId, pageRequestDto);

        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

    @Operation(summary = "방명록 등록")
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/minihome/{hpId}/guest")
    public ResponseEntity<?> addComment(@PathVariable("hpId") Long hpId, @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestBody GuestCommentDto guestCommentDto) throws PostNotFoundException, BadRequestException {

        if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), guestCommentDto.getUserId())) {
            throw new BadRequestException();
        }

        guestRoomService.addComment(hpId, guestCommentDto);

        return new ResponseEntity<>("등록 완료 되었습니다.", HttpStatus.OK);
    }

    @Operation(summary = "방명록 등록 및 수정")
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/{userId}/guest")
    public ResponseEntity<?> modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                    @RequestBody GuestCommentDto guestCommentDto) throws PostNotFoundException, BadRequestException {

        if (!Objects.equals(customUserDetails.getAccountDto().getUserId(), guestCommentDto.getUserId())) {
            throw new BadRequestException();
        }

        ApiResponseDto apiResponseDto = guestRoomService.modify(guestCommentDto);

        return new ResponseEntity<>(apiResponseDto.getMessage(), apiResponseDto.getStatus());
    }


    @Operation(summary = "방명록 삭제")
    @DeleteMapping("/minihome/{hpId}/guest/{commentId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> remove(@AuthenticationPrincipal CustomUserDetails customUserDetails, @PathVariable("hpId") Long hpId, @PathVariable("commentId") Long commentId) throws PostNotFoundException, PermissionDeniedException {

        if (customUserDetails != null) {
            Long userId = customUserDetails.getAccountDto().getUserId();
            guestRoomService.remove(userId, hpId, commentId);
            return new ResponseEntity<>("방명록 삭제가 완료되었습니다.", HttpStatus.OK);
        }

        throw new PermissionDeniedException();
    }
}
