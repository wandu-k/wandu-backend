package com.example.wandukong.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.ShopSubCategory;
import com.example.wandukong.repository.ShopInfo.CategoryRepository;
import com.example.wandukong.repository.ShopInfo.ShopSubCategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartUpInitService implements
        ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final ShopSubCategoryRepository shopSubCategoryRepository;

    // 초기 카테고리 테이블 데이터 삽입 자동화
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Category avatar = Category.builder().categoryId(1L).categoryName("아바타").build();

        categoryRepository.findById(avatar.getCategoryId()).orElseGet(() -> {
            return categoryRepository.save(avatar);
        });

        Category music = Category.builder().categoryId(2L).categoryName("음악").build();

        categoryRepository.findById(music.getCategoryId()).orElseGet(() -> {
            return categoryRepository.save(music);
        });

        ShopSubCategory head = ShopSubCategory.builder().category(avatar).subcategoryId(1L).subcategoryName("머리")
                .build();

        shopSubCategoryRepository.findById(head.getSubcategoryId()).orElseGet(() -> {
            return shopSubCategoryRepository.save(head);
        });

        ShopSubCategory eye = ShopSubCategory.builder().category(avatar).subcategoryId(2L).subcategoryName("눈")
                .build();

        shopSubCategoryRepository.findById(eye.getSubcategoryId()).orElseGet(() -> {
            return shopSubCategoryRepository.save(eye);
        });

        ShopSubCategory mouse = ShopSubCategory.builder().category(avatar).subcategoryId(3L).subcategoryName("입")
                .build();

        shopSubCategoryRepository.findById(mouse.getSubcategoryId()).orElseGet(() -> {
            return shopSubCategoryRepository.save(mouse);
        });

        ShopSubCategory cloth = ShopSubCategory.builder().category(avatar).subcategoryId(4L).subcategoryName("옷")
                .build();

        shopSubCategoryRepository.findById(cloth.getSubcategoryId()).orElseGet(() -> {
            return shopSubCategoryRepository.save(cloth);
        });

        ShopSubCategory ballade = ShopSubCategory.builder().category(music).subcategoryId(5L).subcategoryName("발라드")
                .build();

        shopSubCategoryRepository.findById(ballade.getSubcategoryId()).orElseGet(() -> {
            return shopSubCategoryRepository.save(ballade);
        });
    }

}
