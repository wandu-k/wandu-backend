package com.example.wandukong.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.example.wandukong.domain.ForumBoard;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.ForumBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.ForumPost;
import com.example.wandukong.dto.ForumPostDto;
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.repository.ForumPostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.example.wandukong.exception.CustomException.*;

@Service
@RequiredArgsConstructor
public class ForumPostServiceImpl implements ForumPostService {

  private final ForumPostRepository forumPostRepository;
  private final ForumBoardRepository forumBoardRepository;

  @Override
  public ForumPostDto get(Long postId) throws PostNotFoundException {

    ForumPost forumPost = forumPostRepository.findById(postId).orElseThrow(PostNotFoundException::new);

    forumPost.changeCount(forumPost.getCount());

      return ForumPostDto.builder()
            .postId(forumPost.getPostId())
            .boardId(forumPost.getForumBoard().getBoardId())
            .userId(forumPost.getUserDo().getUserId())
            .title(forumPost.getTitle())
            .content(forumPost.getContent())
            .writeDate(forumPost.getWriteDate())
              .count(forumPost.getCount())
            .build();
  }

  @Transactional
  @Override
  public ApiResponse modify(ForumPostDto forumPostDto) throws BoardNotFoundException {
    ForumBoard forumBoard = forumBoardRepository.findById(forumPostDto.getBoardId()).orElseThrow(BoardNotFoundException::new);

    Optional<ForumPost> result = forumPostRepository.findById(forumPostDto.getPostId());

    if (result.isPresent()) {
      result.get().changePost(forumBoard, forumPostDto.getTitle(), forumPostDto.getContent(), forumPostDto.getState());

      return ApiResponse.builder()
              .message("게시글 수정이 완료되었습니다.")
              .status(HttpStatus.OK)
              .build();
    } else {
      ForumPost forumPost = ForumPost.builder()
              .forumBoard(forumBoard)
              .userDo(UserDo.builder().userId(forumPostDto.getUserId()).build())
              .title(forumPostDto.getTitle())
              .content(forumPostDto.getContent())
              .writeDate(forumPostDto.getWriteDate())
              .state(forumPostDto.getState())
              .build();
      forumPostRepository.save(forumPost);

      return ApiResponse.builder()
              .message("게시글 등록이 완료되었습니다.")
              .status(HttpStatus.CREATED)
              .build();
    }
  }

  @Override
  public void remove(Long userId, Long postId) throws PostNotFoundException, PermissionDeniedException{
    ForumPost forumPost = forumPostRepository.findById(postId).orElseThrow(PostNotFoundException::new);

    // 사용자의 id를 가져와서 파라미터로 넘어온 userId와 비교함(이 코드를 쓰는 이유는 nullPointerExcption을 피하면서 비교를 할 수 있음)
    if (!Objects.equals(forumPost.getUserDo().getUserId(), userId)) {
      throw new PermissionDeniedException();
    }

    forumPostRepository.deleteById(postId);
  }

  @Override
  public PageResponseDto<ForumPostDto> getList(PageRequestDto pageRequestDto) {

    Page<ForumPost> result = forumPostRepository.search(pageRequestDto);

    List<ForumPost> posts = result.getContent();

    List<ForumPostDto> dtoList = new ArrayList<>();
    for (ForumPost post : posts) {
      ForumPostDto forumPostDto = new ForumPostDto();
      forumPostDto = ForumPostDto.builder()
              .postId(post.getPostId())
              .boardId(post.getForumBoard().getBoardId())
              .userId(post.getUserDo().getUserId())
              .title(post.getTitle())
              .content(post.getContent())
              .writeDate(post.getWriteDate())
              .state(post.getState())
              .build();
      dtoList.add(forumPostDto);
    }

      return PageResponseDto.<ForumPostDto>withAll()
        .dtoList(dtoList)
        .pageRequestDto(pageRequestDto)
        .total(result.getTotalElements())
        .build();
  }

}
