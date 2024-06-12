package com.example.wandukong.controller.general;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.ShopInfo.ShopSubCategoryDto;
import com.example.wandukong.service.ShopInfo.ShopSubCategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public/shop/subcategory")
public class PublicShopSubCategoryController {

    private final ShopSubCategoryService shopSubCategoryService;

    @GetMapping
    public ResponseEntity<?> getListSubCategory(@RequestParam(required = false, value = "categoryId") Long categoryId) {
        List<ShopSubCategoryDto> ShopSubCategoryDto = shopSubCategoryService.getListSubCategory(categoryId);
        return new ResponseEntity<>(ShopSubCategoryDto, HttpStatus.OK);
    }

}
