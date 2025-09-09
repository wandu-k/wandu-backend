package com.example.wandukong.repository.ask.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.wandukong.domain.ask.Ask;
import com.example.wandukong.domain.ask.QAsk;
import com.example.wandukong.dto.page.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;

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
