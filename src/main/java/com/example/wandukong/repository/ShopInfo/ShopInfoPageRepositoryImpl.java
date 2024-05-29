package com.example.wandukong.repository.ShopInfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.wandukong.domain.QUserDo;
import com.example.wandukong.domain.ShopInfo.QShop;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShopInfoPageRepositoryImpl implements ShopInfoPageRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Page<ShopInfoDto> SearchAndfindAll(PageRequestDto pageRequestDto, SearchItemDto searchDiaryDto) {
    QShop shop = QShop.shop;
    QUserDo userDo = QUserDo.userDo;

    BooleanBuilder builder = new BooleanBuilder();

    if (searchDiaryDto.getUserId() != null) {
      builder.and(shop.userDo.userId.eq(searchDiaryDto.getUserId()));
    }

    List<Shop> shops = jpaQueryFactory.selectFrom(shop)
        .leftJoin(shop.userDo, userDo).fetchJoin()
        .where(builder)
        .offset(pageRequestDto.getOffset())
        .limit(pageRequestDto.getSize())
        .fetch();
    // Convert Shop entities to ShopInfoDto
    List<ShopInfoDto> shopInfoDtos = shops.stream()
        .map(s -> ShopInfoDto.builder()
            .userId(s.getUserDo().getUserId())
            .itemId(s.getItemId())
            .nickname(s.getUserDo().getNickname())
            .itemName(s.getItemName())
            .build())
        .collect(Collectors.toList());

    long total = jpaQueryFactory.selectFrom(shop).where(builder).fetch().size();

    return new PageImpl<>(shopInfoDtos, Pageable.unpaged(), total);

  }
}
