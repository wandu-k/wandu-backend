package com.example.wandukong.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.QFriend;
import com.example.wandukong.domain.QUserDo;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.UserDto;
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

                UserDo userDo = jpaQueryFactory.selectFrom(userDo1)
                                .where(userDo1.userId.eq(userId))
                                .fetchOne();

                Long followCount = jpaQueryFactory
                                .select(friend.count())
                                .from(friend)
                                .where(friend.userDo.userId.eq(userId))
                                .fetchOne();

                followCount = followCount != null ? followCount : 0L;

                Long followerCount = jpaQueryFactory.select(friend.count()).from(friend)
                                .where(friend.friendDo.userId.eq(userId)).fetchOne();

                followerCount = followerCount != null ? followerCount : 0L;

                UserDto userDto = UserDto.builder()
                                .userId(userDo.getUserId())
                                .nickname(userDo.getNickname())
                                .birthday(userDo.getBirthday())
                                .signupDay(userDo.getSignupDay())
                                .followCount(followCount)
                                .followerCount(followerCount)
                                .build();

                return userDto;
        }

}
