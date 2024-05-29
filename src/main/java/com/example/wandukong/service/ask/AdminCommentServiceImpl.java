package com.example.wandukong.service.ask;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ask.AdminComment;
import com.example.wandukong.domain.ask.Ask;
import com.example.wandukong.dto.ask.AdminCommentDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.ask.AdminCommentRepository;
import com.example.wandukong.repository.ask.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final AskRepository askRepository;
    private final AdminCommentRepository adminCommentRepository;

    @Transactional
    @Override
    public ApiResponseDto modify(AdminCommentDto adminCommentDto) throws PostNotFoundException {
        Ask ask = askRepository.findById(adminCommentDto.getAskId()).orElseThrow(PostNotFoundException::new);

        Optional<AdminComment> result = adminCommentRepository.findById(adminCommentDto.getCommentId());

        if (result.isPresent()) {
            result.get().changeContent(adminCommentDto.getCommentContent());

            return ApiResponseDto.builder()
                    .message("댓글 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            AdminComment adminComment = AdminComment.builder()
                    .userDo(UserDo.builder().userId(adminCommentDto.getUserId()).build())
                    .ask(ask)
                    .commentContent(adminCommentDto.getCommentContent())
                    .writeDate(adminCommentDto.getWriteDate())
                    .build();
            adminCommentRepository.save(adminComment);
            return ApiResponseDto.builder()
                    .message("댓글 등록이 완료되었습니다.")
                    .status(HttpStatus.CREATED)
                    .build();
        }
    }

    @Override
    public PageResponseDto<AdminCommentDto> getList(PageRequestDto pageRequestDto) {
        Page<AdminComment> result = adminCommentRepository.search(pageRequestDto);

        List<AdminComment> comments = result.getContent();

        List<AdminCommentDto> dtoList = new ArrayList<>();
        for (AdminComment comment : comments) {
            AdminCommentDto adminCommentDto = AdminCommentDto.builder()
                    .commentId(comment.getCommentId())
                    .userId(comment.getUserDo().getUserId())
                    .askId(comment.getAsk().getAskId())
                    .commentContent(comment.getCommentContent())
                    .writeDate(comment.getWriteDate())
                    .build();
            dtoList.add(adminCommentDto);
        }

        return PageResponseDto.<AdminCommentDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .total(result.getTotalElements())
                .build();
    }

    @Override
    public void remove(Long userId, Long commentId) throws PostNotFoundException, PermissionDeniedException {
        AdminComment adminComment = adminCommentRepository.findById(commentId).orElseThrow(PostNotFoundException::new);

        if (!Objects.equals(adminComment.getUserDo().getUserId(), userId)) {
            throw new PermissionDeniedException();
        }

        adminCommentRepository.deleteById(commentId);
    }
}
