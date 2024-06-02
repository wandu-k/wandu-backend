package com.example.wandukong.service.ShopInfo;

import java.util.List;

import com.example.wandukong.dto.ShopInfo.ShopSubCategoryDto;

public interface ShopSubCategoryService {

    List<ShopSubCategoryDto> getListSubCategory(Long categoryId);

}
