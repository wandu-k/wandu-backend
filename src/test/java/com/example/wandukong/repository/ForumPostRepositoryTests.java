package com.example.wandukong.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ForumPostRepositoryTests {

  @Autowired
  private ForumPostRepository forumPostRepository;

  @Test
  public void nullTest() {

    Assertions.assertNotNull(forumPostRepository);

  }
}
