package com.example.wandukong.repository;

import java.util.Optional;

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
public class AccountRepositoryImpl extends QuerydslRepositorySupport implements AccountRepositoryCustom {

    public AccountRepositoryImpl(EntityManager entityManager) {
        super(UserDo.class);
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public UserDto getUserInfo(Long userId) {

        QUserDo userDo1 = QUserDo.userDo1;
        QFriend friend = QFriend.friend;

        UserDo userDo = jpaQueryFactory.selectFrom(userDo1)
                .where(userDo1.userId.eq(userId))
                .fetchOne();

        Long followCount = jpaQueryFactory
                .select(friend.count())
                .from(friend)
                .where(friend.userDo.userId.eq(userId))
                .fetchOne();

        UserDto userDto = new UserDto(userDo, followCount);

        return userDto;

    }

}
