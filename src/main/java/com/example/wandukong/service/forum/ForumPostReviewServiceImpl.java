package com.example.wandukong.service.forum;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.forum.ForumPost;
import com.example.wandukong.domain.forum.ForumPostReview;
import com.example.wandukong.dto.forum.ForumPostReviewDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.forum.ForumPostRepository;
import com.example.wandukong.repository.forum.ForumPostReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForumPostReviewServiceImpl implements ForumPostReviewService{

    private final ForumPostRepository forumPostRepository;
    private final ForumPostReviewRepository forumPostReviewRepository;

    @Transactional
    @Override
    public ApiResponseDto modify(ForumPostReviewDto forumPostReviewDto) throws PostNotFoundException {
        ForumPost forumPost = forumPostRepository.findById(forumPostReviewDto.getPostId()).orElseThrow(PostNotFoundException::new);

        Optional<ForumPostReview> result = forumPostReviewRepository.findById(forumPostReviewDto.getCommentId());

        if (result.isPresent()) {
            result.get().changeContent(forumPostReviewDto.getCommentContent());

            return ApiResponseDto.builder()
                    .message("댓글 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            ForumPostReview forumPostReview = ForumPostReview.builder()
                    .forumPost(forumPost)
                    .userDo(UserDo.builder().userId(forumPostReviewDto.getUserId()).build())
                    .commentContent(forumPostReviewDto.getCommentContent())
                    .writeDate(forumPostReviewDto.getWriteDate())
                    .build();
            forumPostReviewRepository.save(forumPostReview);
            return ApiResponseDto.builder()
                    .message("댓글 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public PageResponseDto<ForumPostReviewDto> getList(PageRequestDto pageRequestDto) {
        Page<ForumPostReview> result = forumPostReviewRepository.search(pageRequestDto);

        List<ForumPostReview> reviews = result.getContent();

        List<ForumPostReviewDto> dtoList = new ArrayList<>();
        for (ForumPostReview review : reviews) {
            ForumPostReviewDto forumPostReviewDto = ForumPostReviewDto.builder()
                    .commentId(review.getCommentId())
                    .postId(review.getForumPost().getPostId())
                    .userId(review.getUserDo().getUserId())
                    .commentContent(review.getCommentContent())
                    .writeDate(review.getWriteDate())
                    .build();
            dtoList.add(forumPostReviewDto);
        }

        return PageResponseDto.<ForumPostReviewDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .total(result.getTotalElements())
                .build();
    }

    @Override
    public void remove(Long userId, Long commentId) throws PostNotFoundException, PermissionDeniedException {
        ForumPostReview forumPostReview = forumPostReviewRepository.findById(commentId).orElseThrow(PostNotFoundException::new);

        if (!Objects.equals(forumPostReview.getUserDo().getUserId(), userId)) {
            throw new PermissionDeniedException();
        }

        forumPostReviewRepository.deleteById(commentId);
    }
}
