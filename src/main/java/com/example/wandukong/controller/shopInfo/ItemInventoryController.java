package com.example.wandukong.controller.shopInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.service.ShopInfo.InventoryItemService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

//아이템 구매/구매내역
@RestController
@RequestMapping("api/user/buy")
public class ItemInventoryController {

  @Autowired
  InventoryItemService buyitemservice;

  // 구매한 아이템 내역
  @SecurityRequirement(name = "Baerer Authentication")
  @GetMapping("/{nickname}/inventory")
  public ResponseEntity<?> inventory(MultipartFile multipartFile,
      @AuthenticationPrincipal CustomUserDetails customUserDetails,
      @PathVariable String nickName, @RequestBody PageRequestDto pageRequestDto, @RequestBody BuyItemDto buyitemDto)
      throws UserNotFoundException {

    if (customUserDetails != null) {
      Long loginUser = customUserDetails.getAccountDto().getUserId();
      if (loginUser.equals(buyitemDto.getUserId())) {
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
}
