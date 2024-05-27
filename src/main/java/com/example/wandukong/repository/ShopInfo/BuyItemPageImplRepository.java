package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.QBuyItem;
import com.example.wandukong.dto.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class BuyItemPageImplRepository extends QuerydslRepositorySupport implements BuyItemPageRepository {

  public BuyItemPageImplRepository() {
    super(BuyItem.class);
  }

  @Override
  public Page<BuyItem> findByUserDoUserId(UserDo user, PageRequestDto pageRequestDto) {

    QBuyItem buyItem = QBuyItem.buyItem;
    JPQLQuery<BuyItem> query = from(buyItem);

    query.where(buyItem.userDo.eq(user))
        .leftJoin(buyItem.shop).fetchJoin();

    List<BuyItem> buyItems = query.offset(pageRequestDto.getOffset())
        .limit(pageRequestDto.getSize())
        .fetch();

    long total = query.fetchCount();

    return new PageImpl<>(buyItems, pageRequestDto.of(), total);
  }

}
