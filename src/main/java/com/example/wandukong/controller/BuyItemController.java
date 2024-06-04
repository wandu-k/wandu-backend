package com.example.wandukong.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.EntityAlreadyExistsException;
import com.example.wandukong.service.ShopInfo.InventoryItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user/item")
public class BuyItemController {

    private final InventoryItemService inventoryItemService;

    @PostMapping
    public ResponseEntity<?> addItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody BuyItemDto buyItemDto) throws BadRequestException, EntityAlreadyExistsException {

        log.info(buyItemDto.toString());

        if (customUserDetails.getAccountDto().getUserId() != buyItemDto.getUserId()) {
            throw new BadRequestException();
        }

        inventoryItemService.addItem(buyItemDto);

        return new ResponseEntity<>("구매 완료", HttpStatus.OK);
    }

}
