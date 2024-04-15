package com.example.wandukong.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.exception.CustomException.itemUploadNotFoundException;
import com.example.wandukong.exception.CustomException.itemlistNotFoundException;
import com.example.wandukong.service.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/shop")
public class ShopController {

  @Autowired
  ShopService shopservice;

  // 업로드된 아이템 정보(아이템정보, 아이템 이미지, 등록자 조회)
  @GetMapping("/itemlist")
  public ResponseEntity<?> getShopitemlist() throws itemlistNotFoundException {

    List<ShopInfoDto> shopitemList = new ArrayList<ShopInfoDto>();

    shopitemList = shopservice.getShopitemList();

    if (shopitemList.isEmpty()) {
      return new ResponseEntity<>("현재 등록된 아이템이 없습니다", HttpStatus.OK);
    }
    

    return new ResponseEntity<>(shopitemList, HttpStatus.OK);

  }

  // 내가 등록한 아이템

  // 아이템 정보 및 아이템 파일 업로드"
  @Operation(summary = "아이템 정보 추가/수정")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "아이템 수정이 완료되었습니다."),
      @ApiResponse(responseCode = "201", description = "아이템 등록이 완료되었습니다."),
      @ApiResponse(responseCode = "400", description = "오류 입니다.")
  })
  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping("/itemupload")
  public ResponseEntity<?> itemupload(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestPart(required = false, value = "itemfile") MultipartFile itemfile,
      @RequestPart(value = "shopInfoDto") @Parameter(schema = @Schema(type = "string", format = "binary")) ShopInfoDto shopInfoDto,
      @PathVariable Long itemID) throws itemUploadNotFoundException, UserNotFoundException {

    if (customUserDetails.getUserDto().getUserID() != null) {

      if (shopInfoDto.getShopDto().getItemID() != null) {

        shopservice.updateItemFile(itemfile, shopInfoDto, customUserDetails);

        return new ResponseEntity<>("아이템 정보 수정이 완료되었습니다.", HttpStatus.OK);
      }
      shopservice.putPost(itemfile, shopInfoDto, customUserDetails);

      return new ResponseEntity<>("아이템 등록이 완료되었습니다.", HttpStatus.CREATED);

    } else {
      return new ResponseEntity<>("인증되지않은 이용자입니다.", HttpStatus.UNAUTHORIZED);
    }
  }

}
