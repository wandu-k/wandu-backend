package com.example.wandukong.repository.miniHome.search;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.dto.page.PageRequestDto;

public interface MiniHomePostSearchRepo {

    Page<MiniHomePost> search(PageRequestDto pageRequestDto);

}
