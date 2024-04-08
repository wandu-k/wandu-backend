package com.example.wandukong.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.wandukong.dto.ForumPostDto;

@SpringBootTest
public class ForumPostServiceTests {

  @Autowired
  ForumPostService forumPostService;

  @Test
  public void testGet() {

    Long postID = 11L;

    forumPostService.get(postID);
  }

  @Test
  public void testRegister() {
    ForumPostDto forumPostDto = ForumPostDto.builder()
      .title("Board register")
      .content("register test")
      .writeDate(LocalDate.of(2024, 4, 8))
      .state(1)
      .build();

    forumPostService.register(forumPostDto);
  }

  @Test
  public void testModify() {
    Long postID = 11L;

    ForumPostDto forumPostDto = ForumPostDto.builder()
      .postID(postID)
      .title("Board modify")
      .content("modify test")
      .state(0)
      .build();

    forumPostService.modify(forumPostDto);

  }

  @Test
  public void testRemove() {
    Long postID = 12L;
    
    // 삭제 전에 해당 값이 존재하는 지 확인
    assertNotNull(forumPostService.get(postID));

    // remove 메서드 호출
    forumPostService.remove(postID);

  }


}
