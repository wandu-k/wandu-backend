package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.dto.PageRequestDto;

public interface BuyItemPageRepository {

  Page<BuyItem> findByUserDoUserId(UserDo user, PageRequestDto pageRequestDto);

}
