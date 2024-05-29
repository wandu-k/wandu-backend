package com.example.wandukong.repository.forum.search;

import com.example.wandukong.domain.forum.ForumPostReview;
import com.example.wandukong.dto.page.PageRequestDto;
import org.springframework.data.domain.Page;

public interface ForumPostReviewSearch {

    Page<ForumPostReview> search(PageRequestDto pageRequestDto);
}
