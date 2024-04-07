package com.example.wandukong.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ForumPostServiceTests {

  @Autowired
  ForumPostService forumPostService;

  @Test
  public void testGet() {

    Long postID = 11L;

    forumPostService.get(postID);
  }
}
