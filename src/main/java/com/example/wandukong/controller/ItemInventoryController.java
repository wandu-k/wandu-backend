package com.example.wandukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.service.ShopInfo.BuyItemService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//아이템 구매/구매내역
@RestController
@RequestMapping("api/user/buy")
public class ItemInventoryController {

  @Autowired
  BuyItemService buyitemservice;

  // 구매한 아이템 내역
  @SecurityRequirement(name = "Baerer Authentication")
  @GetMapping("/{nickname}/inventory")
  public ResponseEntity<?> inventory(MultipartFile multipartFile,
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable String nickName, @RequestBody PageRequestDto pageRequestDto, @RequestBody BuyItemDto buyitemDto)
      throws UserNotFoundException {

    if (customUserDetails != null) {
      AccountDto loginUser = customUserDetails.getAccountDto();
      if (loginUser.getUserId().equals(buyitemDto.getUserId())) {
        if (buyitemDto != null) {
          PageResponseDto<BuyItemAllDto> responseDto = buyitemservice.getMybuylist(pageRequestDto, loginUser);
          return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
          return new ResponseEntity<>("구매한 아이템이 없습니다.", HttpStatus.OK);
        }
      } else {
        return new ResponseEntity<>("해당 아이템의 정보에 대한 권한이 없습니다.", HttpStatus.FORBIDDEN);
      }
    } else {
      return new ResponseEntity<>("로그인 후 이용해주세요.", HttpStatus.UNAUTHORIZED);
    }
  }

  // 아이템 구매
  @SecurityRequirement(name = "Baerer Authentication")
  @PostMapping("/purchase")
  public ResponseEntity<?> purchaseItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestBody ShopInfoDto shopInfoDto) throws UserNotFoundException {
    if (customUserDetails != null) {
      buyitemservice.purchaseItem(shopInfoDto, customUserDetails.getAccountDto());

      return new ResponseEntity<>(shopInfoDto, HttpStatus.OK);
    } else {
      return new ResponseEntity<>("로그인 후 이용해주세요.", HttpStatus.UNAUTHORIZED);
    }
  }
}
