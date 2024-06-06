package com.example.wandukong.service;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.repository.ShopInfo.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartUpInitService implements
        ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;

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
    }

}
