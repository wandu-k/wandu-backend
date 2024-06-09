package com.example.wandukong.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.QFriend;
import com.example.wandukong.domain.QUserDo;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.QBuyItem;
import com.example.wandukong.domain.ShopInfo.QShop;
import com.example.wandukong.dto.MyStatisticsDto;
import com.example.wandukong.dto.UserDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

        @Autowired
        private JPAQueryFactory jpaQueryFactory;

        @Override
        public UserDto getUserInfo(Long userId) {

                QUserDo userDo1 = QUserDo.userDo;
                QFriend friend = QFriend.friend;

                Tuple result = jpaQueryFactory
                                .select(userDo1, friend.count().as("followCount"), friend.count().as("followerCount"))
                                .from(userDo1)
                                .leftJoin(friend).on(friend.userDo.userId.eq(userId))
                                .leftJoin(friend).on(friend.friendDo.userId.eq(userId))
                                .where(userDo1.userId.eq(userId))
                                .fetchOne();

                UserDo userDo = result.get(userDo1);
                Long followCount = result.get(1, Long.class);
                Long followerCount = result.get(2, Long.class);

                UserDto userDto = UserDto.builder()
                                .userId(userDo.getUserId())
                                .nickname(userDo.getNickname())
                                .birthday(userDo.getBirthday())
                                .signupDay(userDo.getSignupDay())
                                .role(userDo.getRole())
                                .followCount(followCount)
                                .followerCount(followerCount)
                                .build();

                return userDto;
        }

        @Override
        public MyStatisticsDto getMyStatistics(Long userId) {
                QShop shop = QShop.shop;
                QBuyItem buyItem = QBuyItem.buyItem;

                // 상점과 상품 구매 테이블을 조인하여 필요한 통계를 한 번에 계산
                Tuple tuple = jpaQueryFactory
                                .select(shop.count(), buyItem.count(), buyItem.countDistinct())
                                .from(shop)
                                .leftJoin(shop.buyItem, buyItem)
                                .where(shop.userDo.userId.eq(userId))
                                .fetchOne();

                // 튜플에서 각 통계를 추출하여 MyStatisticsDto로 변환
                Long shopCount = tuple.get(shop.count());
                Long soldItemCount = tuple.get(buyItem.count());
                Long distinctBoughtItemCount = tuple.get(buyItem.countDistinct());

                // MyStatisticsDto 객체 반환
                return new MyStatisticsDto(shopCount.intValue(), soldItemCount.intValue(),
                                distinctBoughtItemCount.intValue());
        }
}
