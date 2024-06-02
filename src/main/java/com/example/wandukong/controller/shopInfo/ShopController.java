package com.example.wandukong.controller.shopInfo;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.service.ShopInfo.ShopService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/user/shop")
public class ShopController {

  @Autowired
  ShopService shopService;

  @Operation(summary = "아이템 리스트")
  @PostMapping
  @SecurityRequirement(name = "Bearer Authentication")
  public ResponseEntity<?> getShopItemList(
      @RequestBody SearchItemDto searchItemDto) {

    log.info(searchItemDto.toString());

    // pageRequestDto 페이지 정보
    // 검색 조회 정보
    PageResponseDto<ShopInfoDto> shopitemList = shopService.getShopItemList(searchItemDto);

    return new ResponseEntity<>(shopitemList, HttpStatus.OK);
  }

  // 아이템 정보 및 아이템 파일 업로드
  @Operation(summary = "아이템 등록")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "아이템 등록이 완료되었습니다."),
      @ApiResponse(responseCode = "200", description = "아이템 수정이 완료되었습니다."),
  })
  @SecurityRequirement(name = "Bearer Authentication")
  @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> putItem(@AuthenticationPrincipal CustomUserDetails customUserDetails,
      @RequestPart(required = true, value = "itemfile") MultipartFile itemfile,
      @RequestPart(value = "shopDto") @Parameter(schema = @Schema(type = "string", format = "binary")) ShopDto shopDto)
      throws IOException, BadRequestException {

    // 프론트에서 보낸 유저번호와 로그인된 유저번호가 일치하는지 확인
    if (customUserDetails.getAccountDto().getUserId() != shopDto.getUserId()) {
      throw new BadRequestException();
    }
    ApiResponseDto apiResponse = shopService.putItem(itemfile, shopDto);

    return new ResponseEntity<>(apiResponse.getMessage(), apiResponse.getStatus());

  }
}
