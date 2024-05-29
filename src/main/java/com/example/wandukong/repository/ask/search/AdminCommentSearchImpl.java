package com.example.wandukong.repository.ask.search;

import com.example.wandukong.domain.ask.AdminComment;
import com.example.wandukong.dto.page.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class AdminCommentSearchImpl extends QuerydslRepositorySupport implements AdminCommentSearch {

    public AdminCommentSearchImpl() {
        super(AdminComment.class);
    }

    @Override
    public Page<AdminComment> search(PageRequestDto pageRequestDto) {
        QAdminComment adminComment = QAdminComment.adminComment;

        JPQLQuery<AdminComment> query = from(adminComment);

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("commentId").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<AdminComment> list = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
