package com.example.wandukong.service.ask;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ask.Ask;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ask.AskDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.model.ApiResponse;
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
public class AskServiceImpl implements AskService {

    private final AskRepository askRepository;

    @Override
    public AskDto get(Long askId) throws PostNotFoundException {

        Ask ask = askRepository.findById(askId).orElseThrow(PostNotFoundException::new);

        ask.changeCount();

        return AskDto.builder()
                .askId(ask.getAskId())
                .userId(ask.getUserDo().getUserId())
                .title(ask.getTitle())
                .content(ask.getContent())
                .writeDate(ask.getWriteDate())
                .solveState(ask.getSolveState())
                .hideState(ask.getHideState())
                .count(ask.getCount())
                .build();
    }

    @Transactional
    @Override
    public ApiResponse modify(AskDto askDto) {
        Optional<Ask> result = askRepository.findById(askDto.getAskId());

        if (result.isPresent()) {
            result.get().changeAsk(askDto.getTitle(), askDto.getContent());

            return ApiResponse.builder()
                    .message("게시글 수정이 완료되었습니다.")
                    .status(HttpStatus.OK)
                    .build();
        } else {
            Ask ask = Ask.builder()
                    .userDo(UserDo.builder().userId(askDto.getUserId()).build())
                    .title(askDto.getTitle())
                    .content(askDto.getContent())
                    .writeDate(askDto.getWriteDate())
                    .solveState(askDto.getSolveState())
                    .hideState(askDto.getHideState())
                    .count(askDto.getCount())
                    .build();

            askRepository.save(ask);
        }

        return ApiResponse.builder()
                .message("게시글 등록이 완료되었습니다.")
                .status(HttpStatus.CREATED)
                .build();
    }

    @Override
    public void remove(Long userId, Long askId) throws PostNotFoundException, PermissionDeniedException {
        Ask ask = askRepository.findById(askId).orElseThrow(PostNotFoundException::new);

        if (!Objects.equals(ask.getUserDo().getUserId(), userId)) {
            throw new PermissionDeniedException();
        }

        askRepository.deleteById(askId);
    }

    @Override
    public PageResponseDto<AskDto> getList(PageRequestDto pageRequestDto) {
        Page<Ask> result = askRepository.search(pageRequestDto);

        List<Ask> asks = result.getContent();

        List<AskDto> dtoList = new ArrayList<>();
        for (Ask ask : asks) {
            // hideState가 0일 때만 리스트에 보이게 하기
            if (ask.getHideState() == 0) {
                AskDto askDto = AskDto.builder()
                        .askId(ask.getAskId())
                        .userId(ask.getUserDo().getUserId())
                        .title(ask.getTitle())
                        .content(ask.getContent())
                        .writeDate(ask.getWriteDate())
                        .solveState(ask.getSolveState())
                        .hideState(ask.getHideState())
                        .count(ask.getCount())
                        .build();
                dtoList.add(askDto);
            }
        }

        return PageResponseDto.<AskDto>withAll()
                .dtoList(dtoList)
                .pageRequestDto(pageRequestDto)
                .total(result.getTotalElements())
                .build();
    }
}
