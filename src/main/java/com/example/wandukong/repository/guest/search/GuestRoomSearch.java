package com.example.wandukong.repository.guest.search;

import com.example.wandukong.domain.guest.GuestRoom;
import com.example.wandukong.dto.page.PageRequestDto;
import org.springframework.data.domain.Page;

public interface GuestRoomSearch {

    Page<GuestRoom> search(PageRequestDto pageRequestDto);
}
