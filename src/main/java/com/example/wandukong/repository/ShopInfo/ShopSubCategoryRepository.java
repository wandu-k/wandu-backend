package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wandukong.domain.ShopInfo.ShopSubCategory;

public interface ShopSubCategoryRepository extends JpaRepository<ShopSubCategory, Long> {

    List<ShopSubCategory> findAllByCategory_CategoryId(Long categoryId);

}
