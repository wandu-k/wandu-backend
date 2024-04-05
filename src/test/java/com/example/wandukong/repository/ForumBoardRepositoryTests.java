package com.example.wandukong.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ForumBoardRepositoryTests {

  @Autowired
  private ForumBoardRepository forumBoardRepository;

  @Test
  public void nullTest() {

    Assertions.assertNotNull(forumBoardRepository);
    
  }

}
