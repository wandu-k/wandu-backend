package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.PageRequestDto;

@Repository
public interface ShopInfoRepository extends JpaRepository<Shop, Long> {

  Shop findByItemID(Long itemID);

  Page<Shop> findAllByCategoryAndItemFileIsNotNull(PageRequestDto pageRequestDto);

}
