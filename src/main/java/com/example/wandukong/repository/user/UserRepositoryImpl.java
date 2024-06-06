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
                QUserDo userDo = QUserDo.userDo;
                QShop shop = QShop.shop; // QShop은 상점 엔티티에 대한 QueryDSL Q타입입니다
                QBuyItem buyItem = QBuyItem.buyItem;

                return jpaQueryFactory
                                .select(Projections.constructor(MyStatisticsDto.class,
                                                JPAExpressions.select(shop.count().intValue()).from(shop)
                                                                .join(shop.userDo, userDo)
                                                                .where(shop.userDo.userId.eq(userId)),
                                                JPAExpressions.select(buyItem.count().intValue()).from(buyItem)
                                                                .join(buyItem.shop, shop)
                                                                .where(shop.userDo.userId.eq(userId)),
                                                JPAExpressions.select(buyItem.count().intValue()).from(buyItem)
                                                                .where(buyItem.userDo.userId.eq(userId))))

                                .from(shop)
                                .fetchOne();
        }
}
