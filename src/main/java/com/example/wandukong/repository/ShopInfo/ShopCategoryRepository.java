package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.ShopInfo.Category;

public interface ShopCategoryRepository extends JpaRepository<Category, Long> {

}
