package com.example.wandukong.repository.search;

import org.springframework.data.domain.Page;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.PageRequestDto;

public interface ForumPostSearch {

  Page<ForumPost> search(PageRequestDto pageRequestDto);
}
