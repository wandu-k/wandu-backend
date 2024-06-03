package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;

public interface ShopInfoRepository extends JpaRepository<Shop, Long>, ShopInfoPageRepository {

}
