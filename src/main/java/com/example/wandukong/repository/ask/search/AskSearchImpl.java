package com.example.wandukong.repository.ask.search;

import com.example.wandukong.domain.Ask;
import com.example.wandukong.domain.QAsk;
import com.example.wandukong.dto.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class AskSearchImpl extends QuerydslRepositorySupport implements AskSearch {
    public AskSearchImpl() {
        super(Ask.class);
    }

    @Override
    public Page<Ask> search(PageRequestDto pageRequestDto) {
        QAsk ask = QAsk.ask;

        JPQLQuery<Ask> query = from(ask);

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(),
                Sort.by("askId").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<Ask> list = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
