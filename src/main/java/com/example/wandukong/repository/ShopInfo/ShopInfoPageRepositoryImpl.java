package com.example.wandukong.repository.ShopInfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.wandukong.domain.QUserDo;
import com.example.wandukong.domain.ShopInfo.QBuyItem;
import com.example.wandukong.domain.ShopInfo.QShop;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.util.S3Util;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ShopInfoPageRepositoryImpl implements ShopInfoPageRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private final S3Util s3Util;

  @Override
  public Page<ShopInfoDto> SearchAndfindAll(SearchItemDto searchItemDto) {

    BooleanBuilder builder = new BooleanBuilder();

    log.info("상점 리파지토리 진입");

    QShop shop = QShop.shop;
    QUserDo userDo = QUserDo.userDo;

    if (searchItemDto.getUserId() != null) {
      builder.and(shop.userDo.userId.eq(searchItemDto.getUserId()));
      log.info("유저 아이디로 조회");
    }

    if (searchItemDto.getCategoryId() != null) {
      builder.and(shop.shopSubcategory.category.categoryId.eq(searchItemDto.getCategoryId()));
      log.info("카테고리 번호로 조회");
    }

    if (searchItemDto.getCategoryName() != null) {
      builder.and(shop.shopSubcategory.category.categoryName.eq(searchItemDto.getCategoryName()));
      log.info("카테고리 이름으로 조회");
    }

    List<Shop> shops = jpaQueryFactory.selectFrom(shop)
        .leftJoin(shop.userDo, userDo).fetchJoin()
        .where(builder)
        .offset(searchItemDto.getOffset())
        .limit(searchItemDto.getSize())
        .fetch();
    // Convert Shop entities to ShopInfoDto
    List<ShopInfoDto> shopInfoDtos = shops.stream()
        .map(s -> ShopInfoDto.builder()
            .userId(s.getUserDo().getUserId())
            .itemId(s.getItemId())
            .nickname(s.getUserDo().getNickname())
            .itemName(s.getItemName())
            .file(s3Util.getUrl(s.getItemFile().getFileName()))
            .price(s.getPrice())
            .subcategoryName(s.getShopSubcategory().getSubcategoryName())
            .categoryId(s.getShopSubcategory().getCategory().getCategoryId())
            .thumbnail(s3Util.getUrl(s.getItemFile().getThumbnail()))
            .build())
        .collect(Collectors.toList());

    long total = jpaQueryFactory.selectFrom(shop).where(builder).fetch().size();

    log.info("조회 완료");

    return new PageImpl<>(shopInfoDtos, Pageable.unpaged(), total);

  }

  @Override
  public ShopInfoDto findByIdWithDto(Long itemId, Long userId) {
    QShop shop = QShop.shop;
    QBuyItem buyItem = QBuyItem.buyItem;

    NumberExpression<Long> purchaseCount = buyItem.shop.itemId.count();

    NumberExpression<Integer> purchaseStatus = new CaseBuilder()
        .when(buyItem.shop.itemId.eq(itemId)).then(1)
        .otherwise(0);

    Long purchaseCountValue = jpaQueryFactory
        .select(purchaseCount)
        .from(buyItem)
        .where(buyItem.shop.itemId.eq(itemId))
        .fetchFirst();

    Integer purchaseStatusValue = jpaQueryFactory
        .select(purchaseStatus)
        .from(buyItem)
        .where(buyItem.userDo.userId.eq(userId))
        .fetchFirst();

    int statusValue = (purchaseStatusValue != null) ? purchaseStatusValue.intValue() : 0;

    Shop s = jpaQueryFactory
        .selectFrom(shop)
        .leftJoin(buyItem)
        .on(buyItem.shop.itemId.eq(shop.itemId))
        .where(shop.itemId.eq(itemId))
        .fetchOne();

    if (s == null) {
      return null;
    }

    ShopInfoDto shopInfoDto = ShopInfoDto.builder()
        .userId(s.getUserDo().getUserId())
        .itemId(s.getItemId())
        .nickname(s.getUserDo().getNickname())
        .itemName(s.getItemName())
        .file(s3Util.getUrl(s.getItemFile().getFileName()))
        .price(s.getPrice())
        .subcategoryName(s.getShopSubcategory().getSubcategoryName())
        .categoryId(s.getShopSubcategory().getCategory().getCategoryId())
        .purchase((int) (purchaseCountValue != null ? purchaseCountValue : 0))
        .purchaseStatus(statusValue)
        .thumbnail(s3Util.getUrl(s.getItemFile().getThumbnail()))
        .build();

    return shopInfoDto;
  }
}
