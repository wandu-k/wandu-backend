package com.example.wandukong.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.QFriend;
import com.example.wandukong.domain.QUserDo;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.UserDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

@Repository
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

        public UserRepositoryImpl(EntityManager entityManager) {
                super(UserDo.class);
                this.jpaQueryFactory = new JPAQueryFactory(entityManager);
        }

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

}
