package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.PageRequestDto;

@Repository
public interface BuyItemRepository extends JpaRepository<BuyItem, Long> {

  Page<BuyItem> findByUserDoUserId(UserDo user, PageRequestDto pageRequestDto);

}
