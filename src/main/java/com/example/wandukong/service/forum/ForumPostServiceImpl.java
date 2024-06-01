package com.example.wandukong.service.forum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wandukong.domain.forum.ForumBoard;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.forum.ForumPostFile;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.forum.ForumBoardRepository;
import com.example.wandukong.repository.forum.ForumPostFileRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.forum.ForumPost;
import com.example.wandukong.dto.forum.ForumPostDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.repository.forum.ForumPostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.example.wandukong.exception.CustomException.*;

@Service
@RequiredArgsConstructor
public class ForumPostServiceImpl implements ForumPostService {

  private final ForumPostRepository forumPostRepository;
  private final ForumBoardRepository forumBoardRepository;
  private final ForumPostFileRepository forumPostFileRepository;
  private final AmazonS3 amazonS3;

  @Value(value = "${cloud.aws.s3.bucket}")
  private String bucketName;

  @Override
  public ForumPostDto get(Long postId) throws PostNotFoundException {

    ForumPost forumPost = forumPostRepository.findById(postId).orElseThrow(PostNotFoundException::new);

    forumPost.changeCount();

    return ForumPostDto.builder()
        .postId(forumPost.getPostId())
        .boardId(forumPost.getForumBoard().getBoardId())
        .userId(forumPost.getUserDo().getUserId())
        .title(forumPost.getTitle())
        .content(forumPost.getContent())
        .writeDate(forumPost.getWriteDate())
        .state(forumPost.getState())
        .count(forumPost.getCount())
        .build();
  }

  @Transactional
  @Override
  public ApiResponseDto modify(MultipartFile postFile, ForumPostDto forumPostDto) throws BoardNotFoundException, IOException {
    ForumBoard forumBoard = forumBoardRepository.findById(forumPostDto.getBoardId())
        .orElseThrow(BoardNotFoundException::new);

    Optional<ForumPost> result = forumPostRepository.findById(forumPostDto.getPostId());
    ForumPostFile forumPostFile = forumPostFileRepository.findById(forumPostDto.getPostId()).orElse(null);

    if (result.isPresent()) {
      result.get().changePost(forumBoard, forumPostDto.getTitle(), forumPostDto.getContent(), forumPostDto.getState());

      if (postFile != null) {
        String postFilePath = postFileUpload(postFile, forumPostDto);
        forumPostFile.changeFileName(postFilePath);
      }

      return ApiResponseDto.builder()
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
          .count(forumPostDto.getCount())
          .build();
      forumPost = forumPostRepository.save(forumPost);

      if (postFile != null) {
        String postFilePath = postFileUpload(postFile, forumPostDto);
        forumPostFile = ForumPostFile.builder()
                .postId(forumPost.getPostId())
                .fileName(postFilePath)
                .build();
        forumPostFileRepository.save(forumPostFile);
      }

      return ApiResponseDto.builder()
          .message("게시글 등록이 완료되었습니다.")
          .status(HttpStatus.CREATED)
          .build();
    }
  }

  private String postFileUpload(MultipartFile postFile, ForumPostDto forumPostDto) throws IOException {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(postFile.getContentType());

    String filePath = "post/" + forumPostDto.getUserId() + "/postFile/";

    String extension = postFile.getOriginalFilename().substring(postFile.getOriginalFilename().lastIndexOf('.'));

    String fileName = "postFile" + "_" + forumPostDto.getUserId() + extension;

    String postFilePath = filePath + fileName;

    amazonS3
            .putObject(new PutObjectRequest(bucketName, postFilePath, postFile.getInputStream(), objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
    return postFilePath;
  }

  @Override
  public void remove(Long userId, Long postId) throws PostNotFoundException, PermissionDeniedException {
    ForumPost forumPost = forumPostRepository.findById(postId).orElseThrow(PostNotFoundException::new);
    forumPostFileRepository.deleteById(postId);
    amazonS3.deleteObject(bucketName, "post/" + userId + "/");
    // 사용자의 id를 가져와서 파라미터로 넘어온 userId와 비교함(이 코드를 쓰는 이유는 nullPointerExcption을 피하면서
    // 비교를 할 수 있음)
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
      if (post.getState() == 1) {
        ForumPostDto forumPostDto = ForumPostDto.builder()
                .postId(post.getPostId())
                .boardId(post.getForumBoard().getBoardId())
                .userId(post.getUserDo().getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .writeDate(post.getWriteDate())
                .state(post.getState())
                .count(post.getCount())
                .build();
        dtoList.add(forumPostDto);
      }
    }

    return PageResponseDto.<ForumPostDto>withAll()
        .dtoList(dtoList)
        .pageRequestDto(pageRequestDto)
        .total(result.getTotalElements())
        .build();
  }

  @Override
  public PageResponseDto<ForumPostDto> adminGetList(PageRequestDto pageRequestDto) {
    Page<ForumPost> result = forumPostRepository.search(pageRequestDto);

    List<ForumPost> posts = result.getContent();

    List<ForumPostDto> dtoList = new ArrayList<>();
    for (ForumPost post : posts) {
      ForumPostDto forumPostDto = ForumPostDto.builder()
              .postId(post.getPostId())
              .boardId(post.getForumBoard().getBoardId())
              .userId(post.getUserDo().getUserId())
              .title(post.getTitle())
              .content(post.getContent())
              .writeDate(post.getWriteDate())
              .state(post.getState())
              .count(post.getCount())
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
