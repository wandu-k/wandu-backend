package com.example.wandukong.repository.diary;

import java.util.List;

import com.example.wandukong.domain.diary.QDiary;
import com.example.wandukong.dto.SearchDiaryDto;
import com.example.wandukong.dto.MiniHome.DiaryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DiaryRepositoryImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<DiaryDto> getList(SearchDiaryDto searchDiaryDto) {

        QDiary diary = QDiary.diary;

        BooleanBuilder builder = new BooleanBuilder();

        if (searchDiaryDto.getDate() != null) {
            builder.and(diary.writeDay.eq(searchDiaryDto.getDate()));
        }
        builder.and(diary.userDo.userId.eq(searchDiaryDto.getUserId()));

        return jpaQueryFactory
                .select(Projections.constructor(DiaryDto.class,
                        diary.postId,
                        diary.userDo.userId,
                        diary.title,
                        diary.content,
                        diary.writeDay))
                .from(diary)
                .where(builder).orderBy(diary.postId.desc()).fetch();
    }
}