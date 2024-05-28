package com.example.wandukong.repository.forum.search;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.forum.ForumPost;
import com.example.wandukong.dto.page.PageRequestDto;

public interface ForumPostSearch {

  Page<ForumPost> search(PageRequestDto pageRequestDto);
}
