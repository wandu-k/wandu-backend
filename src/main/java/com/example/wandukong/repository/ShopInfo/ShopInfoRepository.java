package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.Shop;

@Repository
public interface ShopInfoRepository extends JpaRepository<Shop, Long> {

  Shop findByItemID(Long itemID);

}
