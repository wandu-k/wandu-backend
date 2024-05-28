package com.example.wandukong.service.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.domain.MiniHome.MiniHomeBoard;
import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.MiniHome.MiniHomePostDto;
import com.example.wandukong.exception.CustomException.BoardNotFoundException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponse;
import com.example.wandukong.repository.miniHome.MiniHomeBoardRepository;
import com.example.wandukong.repository.miniHome.MiniHomePostRepository;
import com.example.wandukong.service.MiniHomePostService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminMiniHomePostServiceImpl implements MiniHomePostService {

        @Autowired
        MiniHomePostRepository miniHomePostRepository;

        @Autowired
        MiniHomeBoardRepository miniHomeBoardRepository;

        @Autowired
        ApiResponse apiResponse;

        @Override
        public MiniHomePostDto getPost(Long postId) throws PostNotFoundException {

                MiniHomePost minihomePost = miniHomePostRepository.findById(postId)
                                .orElseThrow(() -> new PostNotFoundException());

                MiniHomePostDto miniHomePostDto = MiniHomePostDto.builder()
                                .postId(minihomePost.getPostId())
                                .boardId(minihomePost.getMiniHomeBoard().getBoardId())
                                .userId(minihomePost.getUserDo().getUserId())
                                .hpId(minihomePost.getMiniHome().getHpId())
                                .title(minihomePost.getTitle())
                                .content(minihomePost.getContent())
                                .writeDay(minihomePost.getWriteDay()).build();

                return miniHomePostDto;
        }

        @Override
        public void deletePost(MiniHomePostDto miniHomePostDto)
                        throws PostNotFoundException {
                MiniHomePost minihomePost = miniHomePostRepository.findById(miniHomePostDto.getPostId())
                                .orElseThrow(() -> new PostNotFoundException());

                if (minihomePost.getUserDo().getUserId() == miniHomePostDto.getUserId()) {
                        miniHomePostRepository.deleteById(minihomePost.getPostId());
                }
        }

        @Transactional
        public ApiResponse putPost(MiniHomePostDto miniHomePostDto) throws BoardNotFoundException {

                MiniHomeBoard miniHomeBoard = miniHomeBoardRepository.findById(miniHomePostDto.getBoardId())
                                .orElseThrow(() -> new BoardNotFoundException());

                Optional<MiniHomePost> optionalMiniHomePost = miniHomePostRepository.findById(
                                miniHomePostDto.getPostId());

                if (optionalMiniHomePost.isPresent()) {
                        log.info("게시글이 이미 있습니다. 게시글 수정을 시작합니다.");
                        optionalMiniHomePost.get()
                                        .updatePost(miniHomeBoard, miniHomePostDto.getTitle(),
                                                        miniHomePostDto.getContent());

                        return ApiResponse.builder()
                                        .message("게시글 수정이 완료되었습니다.")
                                        .status(HttpStatus.OK)
                                        .build();
                } else {
                        log.info("게시글이 없습니다. 게시글 등록을 시작합니다.");
                        MiniHomePost newPost = MiniHomePost.builder()
                                        .userDo(UserDo.builder().userId(miniHomePostDto.getUserId()).build())
                                        .miniHome(MiniHome.builder().hpId(miniHomePostDto.getHpId()).build())
                                        .miniHomeBoard(miniHomeBoard)
                                        .title(miniHomePostDto.getTitle())
                                        .content(miniHomePostDto.getContent())
                                        .build();

                        miniHomePostRepository.save(newPost);

                        return ApiResponse.builder()
                                        .message("게시글 등록이 완료되었습니다.")
                                        .status(HttpStatus.CREATED)
                                        .build();
                }
        }

        @Override
        public PageResponseDto<MiniHomePostDto> getPostList(PageRequestDto pageRequestDto) {

                Page<MiniHomePost> result = miniHomePostRepository.search(pageRequestDto);

                // 페이지 결과에서 포스트 목록 추출
                List<MiniHomePost> posts = result.getContent();

                // 포스트 목록을 MiniHomePostDto로 변환
                List<MiniHomePostDto> dtoList = new ArrayList<>();
                for (MiniHomePost post : posts) {
                        MiniHomePostDto postDto = new MiniHomePostDto();
                        postDto = MiniHomePostDto.builder()
                                        .postId(post.getPostId())
                                        .boardId(post.getMiniHomeBoard().getBoardId())
                                        .userId(post.getUserDo().getUserId())
                                        .hpId(post.getMiniHome().getHpId())
                                        .title(post.getTitle())
                                        .content(post.getContent())
                                        .writeDay(post.getWriteDay())
                                        .build();
                        dtoList.add(postDto);
                }

                PageResponseDto<MiniHomePostDto> responseDto = PageResponseDto.<MiniHomePostDto>withAll()
                                .dtoList(dtoList)
                                .pageRequestDto(pageRequestDto)
                                .total(result.getTotalElements())
                                .build();
                return responseDto;
        }
}