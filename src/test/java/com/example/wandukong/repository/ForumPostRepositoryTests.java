package com.example.wandukong.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.example.wandukong.domain.ForumPost;


@SpringBootTest
public class ForumPostRepositoryTests {

  @Autowired
  private ForumPostRepository forumPostRepository;

  @Test
  public void nullTest() {

    Assertions.assertNotNull(forumPostRepository);

  }

  @Test
  public void testInsert() {
    
    for (int i = 0; i < 20; i++) {
      ForumPost forumPost = ForumPost.builder()
        .title("Board" + i)
        .content("Board content" + i)
        .writeDate(LocalDate.of(2024, 04, 06))
        .state(1)
        .build();

      forumPostRepository.save(forumPost);
    }
    
  }

  @Test
  public void testRead() {

    Long postID = 1L;

    Optional<ForumPost> result = forumPostRepository.findById(postID);

    result.orElseThrow();
  }


  @Test
  public void testUpdate() {

    Long postID = 1L;

    Optional<ForumPost> result = forumPostRepository.findById(postID);

    ForumPost forumPost = result.orElseThrow();

    forumPost.changeTitle("Update board");
    forumPost.changeContent("Update cotent");
    forumPost.changeState(0);

    forumPostRepository.save(forumPost);
  }

  @Test
  public void testDelete() {
    
    Long postID = 1L;

    forumPostRepository.deleteById(postID);
  }

  @Test
  public void testPaging() {

    Pageable pageable = PageRequest.of(0, 10, Sort.by("postID").descending());

    forumPostRepository.findAll(pageable);
  }
}
