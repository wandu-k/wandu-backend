package com.example.wandukong.repository.ShopInfo;

import java.util.List;
import java.util.stream.Collectors;

import com.example.wandukong.domain.ShopInfo.QBgmList;
import com.example.wandukong.domain.ShopInfo.QShop;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.util.S3Util;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BgmListRepositoryCustomImpl implements BgmListRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final S3Util s3Util;

    @Override
    public List<ShopInfoDto> findAllByPlaylistId(Long playlistId) {

        QBgmList bgmList = QBgmList.bgmList;
        QShop shop = QShop.shop;

        List<Shop> shops = jpaQueryFactory.selectFrom(shop)
                .leftJoin(shop.buyItem.bgmList, bgmList)
                .where(shop.itemId.eq(bgmList.buyItem.itemId))
                .fetch();

        // Convert Shop entities to ShopInfoDto
        List<ShopInfoDto> shopInfoDtos = shops.stream()
                .map(s -> ShopInfoDto.builder()
                        .userId(s.getUserDo().getUserId())
                        .itemId(s.getItemId())
                        .nickname(s.getUserDo().getNickname())
                        .itemName(s.getItemName())
                        .file(s3Util.getUrl(s.getItemFile().getFileName()))
                        .subcategoryName(s.getShopSubcategory().getSubcategoryName())
                        .build())
                .collect(Collectors.toList());

        return shopInfoDtos;
    }

}
