package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.QShop;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class ShopInfoPageRepositoryImpl extends QuerydslRepositorySupport implements ShopInfoPageRepository {

  public ShopInfoPageRepositoryImpl() {
    super(Shop.class);
    // TODO Auto-generated constructor stub
  }

  @Override
  public Page<Shop> findAllByCategoryAndItemFileIsNotNull(PageRequestDto pageRequestDto, UserDo userId) {
    QShop shop = QShop.shop;
    JPQLQuery<Shop> query = from(shop);

    query.where(shop.userDo.eq(userId))
        .leftJoin(shop.category).fetchJoin()
        .leftJoin(shop.itemFile).fetchJoin();

    List<Shop> shops = query.offset(pageRequestDto.getOffset())
        .limit(pageRequestDto.getSize())
        .fetch();

    long total = query.fetchCount();

    return new PageImpl<>(shops, pageRequestDto.of(), total);
  }

  @Override
  public Page<Shop> findAllByCategoryAndItemFileIsNotNull(PageRequestDto pageRequestDto) {
    QShop shop = QShop.shop;
    JPQLQuery<Shop> query = from(shop);

    // Querydsl을 사용하여 필요한 정보를 한 번에 가져옴
    List<Shop> shops = query.select(shop)
        .leftJoin(shop.userDo).fetchJoin()
        .leftJoin(shop.category).fetchJoin()
        .leftJoin(shop.itemFile).fetchJoin()
        .offset(pageRequestDto.getOffset())
        .limit(pageRequestDto.getSize())
        .fetch();

    // 전체 엔티티 개수를 가져옴
    long total = query.select(shop)
        .from(shop)
        .fetchCount();

    return new PageImpl<>(shops, Pageable.unpaged(), total);

  }

}
