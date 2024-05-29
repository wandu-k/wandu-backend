package com.example.wandukong.repository.ask;

import com.example.wandukong.domain.ask.AdminComment;
import com.example.wandukong.repository.ask.search.AdminCommentSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCommentRepository extends JpaRepository<AdminComment, Long>, AdminCommentSearch {
}
