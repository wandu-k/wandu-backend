package com.example.wandukong.repository.ShopInfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.QUserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.QBuyItem;
import com.example.wandukong.domain.ShopInfo.QShop;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.InventoryItemDto;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.util.S3Util;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BuyItemPageImplRepository implements BuyItemPageRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final S3Util s3Util;

  @Override
  public Page<InventoryItemDto> findByUserDoUserId(Long userId, SearchItemDto searchItemDto) {
    BooleanBuilder builder = new BooleanBuilder();

    QShop shop = QShop.shop;
    QUserDo userDo = QUserDo.userDo;
    QBuyItem buyItem = QBuyItem.buyItem;

    // UserId로 필터링
    builder.and(buyItem.userDo.userId.eq(userId));

    if (searchItemDto.getCategoryId() != null) {
      builder.and(shop.shopSubcategory.category.categoryId.eq(searchItemDto.getCategoryId()));
      log.info("카테고리 번호로 조회");
    }

    if (searchItemDto.getCategoryName() != null) {
      builder.and(shop.shopSubcategory.category.categoryName.eq(searchItemDto.getCategoryName()));
      log.info("카테고리 이름으로 조회");
    }

    List<Tuple> results = jpaQueryFactory.select(shop, buyItem)
        .from(shop)
        .leftJoin(shop.userDo, userDo)
        .leftJoin(shop.buyItem, buyItem)
        .where(builder)
        .offset(searchItemDto.getOffset())
        .limit(searchItemDto.getSize())
        .fetch();

    List<InventoryItemDto> inventoryItemDtos = results.stream()
        .map(tuple -> {
          Shop shopEntity = tuple.get(shop);
          BuyItem buyItemEntity = tuple.get(buyItem);
          int enable = 0;
          if (buyItemEntity.getHead() != null || buyItemEntity.getEye() != null ||
              buyItemEntity.getMouse() != null || buyItemEntity.getCloth() != null) {
            enable = 1;
          }
          return InventoryItemDto.builder()
              .userId(shopEntity.getUserDo().getUserId())
              .itemId(shopEntity.getItemId())
              .nickname(shopEntity.getUserDo().getNickname())
              .itemName(shopEntity.getItemName())
              .file(s3Util.getUrl(shopEntity.getItemFile().getFileName()))
              .subcategoryName(shopEntity.getShopSubcategory().getSubcategoryName())
              .subcategoryId(shopEntity.getShopSubcategory().getSubcategoryId())
              .enable(enable)
              .build();
        })
        .collect(Collectors.toList());

    long total = jpaQueryFactory.selectFrom(shop)
        .leftJoin(shop.buyItem, buyItem)
        .where(builder)
        .fetch().size();

    return new PageImpl<>(inventoryItemDtos, searchItemDto.of(), total);
  }
}