package com.example.wandukong.repository.miniHome.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.domain.MiniHome.QMiniHomePost;
import com.example.wandukong.dto.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;

public class MiniHomePostSearchRepoImpl extends QuerydslRepositorySupport implements MiniHomePostSearchRepo {

    public MiniHomePostSearchRepoImpl() {
        super(MiniHomePost.class);
    }

    @Override
    public Page<MiniHomePost> search(PageRequestDto pageRequestDto) {
        QMiniHomePost miniHomePost = QMiniHomePost.miniHomePost;

        JPQLQuery<MiniHomePost> query = from(miniHomePost);

        if (pageRequestDto.getBoardId() != null && pageRequestDto.getBoardId() != 0) {
            query.where(miniHomePost.miniHomeBoard.boardId.eq(pageRequestDto.getBoardId()));
        }
        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(),
                Sort.by("postID").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<MiniHomePost> list = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }

}
