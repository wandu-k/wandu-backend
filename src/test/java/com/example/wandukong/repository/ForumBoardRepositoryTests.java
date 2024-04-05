package com.example.wandukong.repository;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.wandukong.domain.ForumBoard;

@SpringBootTest
public class ForumBoardRepositoryTests {

  @Autowired
  private ForumBoardRepository forumBoardRepository;

  @Test
  public void nullTest() {

    Assertions.assertNotNull(forumBoardRepository);

  }

  @Test
  public void testInsert() {
    
    ForumBoard forumBoard = ForumBoard.builder()
      .boardName("Board")
      .build();

    forumBoardRepository.save(forumBoard);
  }

  @Test
  public void testRead() {

    Long boardID = 1L;

    Optional<ForumBoard> result = forumBoardRepository.findById(boardID);

    result.orElseThrow();
  }


  @Test
  public void testUpdate() {

    Long boardID = 1L;

    Optional<ForumBoard> result = forumBoardRepository.findById(boardID);

    ForumBoard forumBoard = result.orElseThrow();

    forumBoard.changeBoardName("Update Board");

    forumBoardRepository.save(forumBoard);
  }

  @Test
  public void testDelete() {
    
    Long boardID = 1L;

    forumBoardRepository.deleteById(boardID);
  }

}
