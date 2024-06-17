package com.example.wandukong.repository.guest.search;

import com.example.wandukong.domain.guest.GuestComment;
import com.example.wandukong.domain.guest.QGuestComment;
import com.example.wandukong.dto.page.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class GuestRoomSearchImpl extends QuerydslRepositorySupport implements GuestRoomSearch {

    public GuestRoomSearchImpl() {
        super(GuestComment.class);
    }

    @Override
    public Page<GuestComment> search(Long hpId, PageRequestDto pageRequestDto) {
        QGuestComment guestComment = QGuestComment.guestComment;

        JPQLQuery<GuestComment> query = from(guestComment).where(guestComment.miniHome.hpId.eq(hpId));

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(),
                Sort.by("commentId").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<GuestComment> list = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
