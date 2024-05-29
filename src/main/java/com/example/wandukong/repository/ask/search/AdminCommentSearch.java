package com.example.wandukong.repository.ask.search;

import com.example.wandukong.domain.ask.AdminComment;
import com.example.wandukong.dto.page.PageRequestDto;
import org.springframework.data.domain.Page;

public interface AdminCommentSearch {

    Page<AdminComment> search(PageRequestDto pageRequestDto);
}
