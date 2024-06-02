package com.example.wandukong.service.ShopInfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.ShopInfo.ShopSubCategory;
import com.example.wandukong.dto.ShopInfo.ShopSubCategoryDto;
import com.example.wandukong.repository.ShopInfo.ShopSubCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopSubCategoryServiceImpl implements ShopSubCategoryService {

    private final ShopSubCategoryRepository shopSubCategoryRepository;

    @Override
    public List<ShopSubCategoryDto> getListSubCategory(Long categoryId) {

        List<ShopSubCategory> list = new ArrayList<>();

        if (categoryId == null) {
            list = shopSubCategoryRepository.findAll();
        } else {
            list = shopSubCategoryRepository.findAllByCategory_CategoryId(categoryId);
        }

        List<ShopSubCategoryDto> shopSubCategoryDtos = new ArrayList<>();

        for (ShopSubCategory shopSubCategory : list) {

            ShopSubCategoryDto shopSubCategoryDto = new ShopSubCategoryDto(shopSubCategory.getSubcategoryId(),
                    shopSubCategory.getSubcategoryName(), shopSubCategory.getCategory().getCategoryId(),
                    shopSubCategory.getCategory().getCategoryName());
            shopSubCategoryDtos.add(shopSubCategoryDto);
        }
        return shopSubCategoryDtos;
    }

}
