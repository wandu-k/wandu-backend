package com.example.wandukong.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.domain.QForumPost;
import com.querydsl.jpa.JPQLQuery;

public class ForumPostSearchImpl extends QuerydslRepositorySupport implements ForumPostSearch{

  public ForumPostSearchImpl() {
    super(ForumPost.class);
  }

  @Override
  public Page<ForumPost> search() {
    
    QForumPost forumPost = QForumPost.forumPost;

    JPQLQuery<ForumPost> query = from(forumPost);

    query.where(forumPost.title.contains("1"));

    Pageable pageable = PageRequest.of(1, 10, Sort.by("postID").descending());

    this.getQuerydsl().applyPagination(pageable, query);

    query.fetch();

    query.fetchCount();

    return null;
  }
  
}
