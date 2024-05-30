package com.example.wandukong.repository.guest.search;

import com.example.wandukong.domain.guest.GuestRoom;
import com.example.wandukong.dto.page.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class GuestRoomSearchImpl extends QuerydslRepositorySupport implements GuestRoomSearch {

    public GuestRoomSearchImpl() {
        super(GuestRoom.class);
    }

    @Override
    public Page<GuestRoom> search(PageRequestDto pageRequestDto) {
        QGuestRoom guestRoom = QGuestRoom.guestRoom;

        JPQLQuery<GuestRoom> query = from(guestRoom);

        Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(),
                Sort.by("commentId").descending());

        this.getQuerydsl().applyPagination(pageable, query);

        List<GuestRoom> list = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(list, pageable, total);
    }
}
