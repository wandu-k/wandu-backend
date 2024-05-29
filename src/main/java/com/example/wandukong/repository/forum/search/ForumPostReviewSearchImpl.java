package com.example.wandukong.repository.forum.search;

import com.example.wandukong.domain.forum.ForumPostReview;
import com.example.wandukong.dto.page.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ForumPostReviewSearchImpl extends QuerydslRepositorySupport implements ForumPostReviewSearch {

    public ForumPostReviewSearchImpl() {
        super(ForumPostReview.class);
    }

    @Override
    public Page<ForumPostReview> search(PageRequestDto pageRequestDto) {
        QForumPostReview forumPostReview = QForumPostReview.forumPostReview;

        JPQLQuery<ForumPostReview> query = from(forumPostReview);

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() -1, pageRequestDto.getSize(),
                Sort.by("commentId").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<ForumPostReview> list = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
