package com.example.wandukong.service.ask;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ask.Ask;
import com.example.wandukong.domain.ask.AskFile;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ask.AskDto;
import com.example.wandukong.exception.CustomException.PostNotFoundException;
import com.example.wandukong.exception.CustomException.PermissionDeniedException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.ask.AskFileRepository;
import com.example.wandukong.repository.ask.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AskServiceImpl implements AskService {

    private final AskRepository askRepository;
    private final AskFileRepository askFileRepository;
    private final AmazonS3 amazonS3;

    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucketName;

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
    public ApiResponseDto modify(MultipartFile askFile, AskDto askDto) throws IOException {
        Optional<Ask> result = askRepository.findById(askDto.getAskId());
        AskFile askPostFile = askFileRepository.findById(askDto.getAskId()).orElse(null);

        if (result.isPresent()) {
            result.get().changeAsk(askDto.getTitle(), askDto.getContent());

            if (askFile != null) {
                String postFilePath = postFileUpload(askFile, askDto);
                askPostFile.changeFileName(postFilePath);
            }

            return ApiResponseDto.builder()
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

            ask = askRepository.save(ask);

            if (askFile != null) {
                String postFilePath = postFileUpload(askFile, askDto);
                askPostFile = AskFile.builder()
                        .askId(ask.getAskId())
                        .fileName(postFilePath)
                        .build();
                askFileRepository.save(askPostFile);
            }
        }

        return ApiResponseDto.builder()
                .message("게시글 등록이 완료되었습니다.")
                .status(HttpStatus.CREATED)
                .build();
    }

    private String postFileUpload(MultipartFile askFile, AskDto askDto) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(askFile.getContentType());

        String filePath = "ask/" + askDto.getUserId() + "/file/";

        String extension = askFile.getOriginalFilename().substring(askFile.getOriginalFilename().lastIndexOf("."));

        String fileName = "askFile" + "_" + askDto.getUserId() + extension;

        String postFilePath = filePath + fileName;

        amazonS3
                .putObject(new PutObjectRequest(bucketName, postFilePath, askFile.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));

        return postFilePath;
    }

    @Override
    public void remove(Long userId, Long askId) throws PostNotFoundException, PermissionDeniedException {
        Ask ask = askRepository.findById(askId).orElseThrow(PostNotFoundException::new);
        askFileRepository.deleteById(askId);
        amazonS3.deleteObject(bucketName, "ask/" + userId + "/");

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
