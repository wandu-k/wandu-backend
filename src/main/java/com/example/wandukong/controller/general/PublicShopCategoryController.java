package com.example.wandukong.controller.general;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.ShopInfo.CategoryDto;
import com.example.wandukong.service.ShopInfo.ShopCategoryService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public/shop/category")
public class PublicShopCategoryController {

    private final ShopCategoryService shopCategoryService;

    @GetMapping
    public ResponseEntity<?> getListCategory() {
        List<CategoryDto> categoryDto = shopCategoryService.getListCategory();
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

}
