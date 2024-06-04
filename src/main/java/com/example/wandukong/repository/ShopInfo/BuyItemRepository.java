package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.BuyItem;

@Repository
public interface BuyItemRepository extends JpaRepository<BuyItem, Long> {
  List<BuyItem> findByUserDoUserId(Long userId);

  BuyItem findByShop_ItemId(Long itemId);

}
