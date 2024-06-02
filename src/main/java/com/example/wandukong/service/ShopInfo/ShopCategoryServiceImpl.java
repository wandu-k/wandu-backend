package com.example.wandukong.service.ShopInfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.dto.ShopInfo.CategoryDto;
import com.example.wandukong.repository.ShopInfo.ShopCategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopCategoryServiceImpl implements ShopCategoryService {

    private final ShopCategoryRepository shopCategoryRepository;

    @Override
    public List<CategoryDto> getListCategory() {
        List<Category> list = shopCategoryRepository.findAll();

        List<CategoryDto> categoryDtolist = new ArrayList<>();

        for (Category category : list) {

            CategoryDto categoryDto = new CategoryDto(category.getCategoryId(), category.getCategoryName());

            categoryDtolist.add(categoryDto);

        }

        return categoryDtolist;
    }

}
