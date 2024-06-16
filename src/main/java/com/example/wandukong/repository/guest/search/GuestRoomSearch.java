package com.example.wandukong.repository.guest.search;

import com.example.wandukong.domain.guest.GuestComment;
import com.example.wandukong.dto.page.PageRequestDto;
import org.springframework.data.domain.Page;

public interface GuestRoomSearch {

    Page<GuestComment> search(Long hpId, PageRequestDto pageRequestDto);
}
