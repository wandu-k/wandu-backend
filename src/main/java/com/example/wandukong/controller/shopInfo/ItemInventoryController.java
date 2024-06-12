package com.example.wandukong.controller.shopInfo;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.InventoryItemDto;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.service.ShopInfo.InventoryItemService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

//아이템 구매/구매내역
@Slf4j
@RestController
@RequestMapping("api/user")
public class ItemInventoryController {

  @Autowired
  InventoryItemService inventoryItemService;

  // 구매한 아이템 내역
  @SecurityRequirement(name = "Bearer Authentication")
  @PostMapping("/{userId}/inventory/list")
  public ResponseEntity<?> inventory(
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable Long userId,
      @ParameterObject SearchItemDto searchItemDto) throws BadRequestException {

    if (!customUserDetails.getAccountDto().getUserId().equals(userId)) {
      throw new BadRequestException();
    }

    log.info(searchItemDto.toString());

    PageResponseDto<InventoryItemDto> responseDto = inventoryItemService.getMybuylist(userId, searchItemDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }
}
