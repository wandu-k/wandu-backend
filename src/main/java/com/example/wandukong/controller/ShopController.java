package com.example.wandukong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ShopDto;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.service.ShopService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/shop")
public class ShopController {

  @Autowired
  private ShopService shopservice;

  @PostMapping("/upload")
  public ResponseEntity<?> ShopItemFileupload(
      @AuthenticationPrincipal CustomUserDetails customUserDetails, @RequestParam("ItemFile") MultipartFile Itemfile,
      @RequestParam("Shop") ShopDto shopDto) {

    UserDto userDto = customUserDetails.getUserDto();
    int result = shopservice.uploadItem(userDto.getUserID(), Itemfile, shopDto);

    if (result == 0) {
      return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }
  }

}
