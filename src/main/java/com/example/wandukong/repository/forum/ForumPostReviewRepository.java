package com.example.wandukong.repository.forum;

import com.example.wandukong.domain.forum.ForumPostReview;
 import com.example.wandukong.repository.forum.search.ForumPostReviewSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumPostReviewRepository extends JpaRepository<ForumPostReview, Long>, ForumPostReviewSearch {
}
