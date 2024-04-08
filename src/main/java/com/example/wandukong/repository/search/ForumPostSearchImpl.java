package com.example.wandukong.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.domain.QForumPost;
import com.example.wandukong.dto.PageRequestDto;
import com.querydsl.jpa.JPQLQuery;

public class ForumPostSearchImpl extends QuerydslRepositorySupport implements ForumPostSearch{

  public ForumPostSearchImpl() {
    super(ForumPost.class);
  }

  @Override
  public Page<ForumPost> search(PageRequestDto pageRequestDto) {
    
    QForumPost forumPost = QForumPost.forumPost;

    JPQLQuery<ForumPost> query = from(forumPost);

    Pageable pageable = PageRequest.of(pageRequestDto.getPage() - 1, pageRequestDto.getSize(), Sort.by("postID").descending());

    this.getQuerydsl().applyPagination(pageable, query);

    List<ForumPost> list = query.fetch();

    long total = query.fetchCount();

    return new PageImpl<>(list, pageable, total);
  }
  
}
