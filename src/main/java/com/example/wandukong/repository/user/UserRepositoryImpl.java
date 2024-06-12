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
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

        @Autowired
        private JPAQueryFactory jpaQueryFactory;

        @Override
        public UserDto getUserInfo(Long userId, Long followCheckUserId) {

                QUserDo userDo1 = QUserDo.userDo;
                QFriend friend = QFriend.friend;

                // followCheck 초기화
                NumberExpression<Integer> followCheck = new CaseBuilder()
                                .when(JPAExpressions
                                                .selectOne()
                                                .from(friend)
                                                .where(friend.friendId.userDo.userId.eq(followCheckUserId)
                                                                .and(friend.friendId.friendDo.userId
                                                                                .eq(userId)))
                                                .exists())
                                .then(1)
                                .otherwise(0);

                // followCheck 초기화
                NumberExpression<Integer> followerCheck = new CaseBuilder()
                                .when(JPAExpressions
                                                .selectOne()
                                                .from(friend)
                                                .where(friend.friendId.userDo.userId.eq(userId)
                                                                .and(friend.friendId.friendDo.userId
                                                                                .eq(followCheckUserId)))
                                                .exists())
                                .then(1)
                                .otherwise(0);

                // Query 수정
                // followCount와 followerCount 조건 정의
                NumberExpression<Long> followCount = new CaseBuilder()
                                .when(friend.friendId.userDo.userId.eq(userId))
                                .then(1L)
                                .otherwise(0L);

                NumberExpression<Long> followerCount = new CaseBuilder()
                                .when(friend.friendId.friendDo.userId.eq(userId))
                                .then(1L)
                                .otherwise(0L);

                // Query 수정
                Tuple result = jpaQueryFactory
                                .select(userDo1,
                                                followCount.sum().as("followCount"),
                                                followerCount.sum().as("followerCount"),
                                                followCheck.as("followCheck"),
                                                followerCheck.as("followerCheck"))
                                .from(userDo1)
                                .leftJoin(friend)
                                .on(friend.friendId.userDo.userId.eq(userId)
                                                .or(friend.friendId.friendDo.userId.eq(userId)))
                                .where(userDo1.userId.eq(userId))
                                .fetchOne();

                UserDo userDo = result.get(userDo1);
                Long followCountValue = result.get(1, Long.class);
                Long followerCountValue = result.get(2, Long.class);
                Integer followCheckValue = result.get(followCheck.as("followCheck"));
                Integer followerCheckValue = result.get(followerCheck.as("followerCheck"));

                UserDto userDto = UserDto.builder()
                                .userId(userDo.getUserId())
                                .nickname(userDo.getNickname())
                                .birthday(userDo.getBirthday())
                                .signupDay(userDo.getSignupDay())
                                .intro(userDo.getIntro())
                                .role(userDo.getRole())
                                .followCount(followCountValue)
                                .followerCount(followerCountValue)
                                .followCheck(followCheckValue)
                                .followerCheck(followerCheckValue)
                                .build();

                return userDto;
        }

        @Override
        public int getShopCount(Long userId) {
                QShop shop = QShop.shop;

                return jpaQueryFactory
                                .select(shop.count())
                                .from(shop)
                                .where(shop.userDo.userId.eq(userId))
                                .fetchOne()
                                .intValue();
        }

        @Override
        public int getSoldItemCount(Long userId) {
                QBuyItem buyItem = QBuyItem.buyItem;

                return jpaQueryFactory
                                .select(buyItem.count())
                                .from(buyItem)
                                .leftJoin(buyItem.shop)
                                .where(buyItem.shop.userDo.userId.eq(userId))
                                .fetchOne()
                                .intValue();
        }

        @Override
        public int getDistinctBoughtItemCount(Long userId) {

                QBuyItem buyItem = QBuyItem.buyItem;

                return jpaQueryFactory
                                .select(buyItem.buyItemId.countDistinct())
                                .from(buyItem)
                                .leftJoin(buyItem.shop)
                                .where(buyItem.userDo.userId.eq(userId))
                                .fetchOne()
                                .intValue();
        }
}
