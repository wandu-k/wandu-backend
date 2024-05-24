package com.example.wandukong.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;
import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.exception.CustomException.IncorrectPasswordException;
import com.example.wandukong.exception.CustomException.UserAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.repository.miniHome.MiniHomeRepository;
import com.example.wandukong.repository.user.UserRepository;
import com.example.wandukong.security.jwt.JwtTokenProvider;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository accountRepository;

    @Autowired
    MiniHomeRepository miniHpRepository;

    @Autowired
    AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AmazonS3 amazonS3;

    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucketName;

    // 회원가입
    @Transactional
    @Override
    public void register(MultipartFile profileImage, AccountDto accountDto)
            throws UserAlreadyExistsException, IOException {
        String encodedPw = passwordEncoder.encode(accountDto.getPassword());

        // Check for duplicate username before saving
        if (accountRepository.findByEmail(accountDto.getUsername()) == null) {

            UserDo userDo = UserDo.builder()
                    .email(accountDto.getUsername())
                    .password(encodedPw)
                    .nickname(accountDto.getNickname())
                    .build();

            userDo = accountRepository.save(userDo);

            if (profileImage != null) {
                String profileImagePath = profileUpload(profileImage, accountDto);
                userDo.updateProfileImage(profileImagePath);
            }

            log.info("회원가입된 회원 아이디" + userDo.getUserId());

            // 회원가입이 완료되면 그 유저아이디로 미니홈도 생성
            MiniHome miniHome = MiniHome.builder()
                    .userDo(userDo)
                    .introduction(userDo.getNickname() + "의 미니홈입니다.")
                    .build();

            log.info("홈피 유저 아이디 : " + miniHome.getUserDo().getUserId());

            miniHome = miniHpRepository.save(miniHome);

        } else {
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    public void deleteAccount(Long userId) {
        accountRepository.deleteById(userId);
        amazonS3.deleteObject(bucketName, "users/" + userId + "/");

    }

    // 유저정보 업데이트
    @Transactional
    @Override
    public void updateProfile(MultipartFile profileImage, AccountDto accountDto)
            throws IOException, UserNotFoundException {

        UserDo userDo = accountRepository.findById(accountDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException());

        if (profileImage != null) {
            String profileImagePath = profileUpload(profileImage, accountDto);
            userDo.updateProfileImage(profileImagePath);
        }
        userDo.updateProfile(accountDto.getUsername(), accountDto.getName(), accountDto.getNickname(),
                accountDto.getBirthday(), accountDto.getPhone(), accountDto.getGender());
    }

    // 프로필 이미지 업로드
    private String profileUpload(MultipartFile profileImage, AccountDto accountDto) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(profileImage.getContentType());

        String filePath = "users/" + accountDto.getUserId() + "/profile/";

        String extension = profileImage.getOriginalFilename()
                .substring(profileImage.getOriginalFilename().lastIndexOf('.'));

        String filename = "profile" + "_" + accountDto.getUserId() + extension;

        String profileImagePath = filePath + filename;

        amazonS3
                .putObject(new PutObjectRequest(bucketName, profileImagePath,
                        profileImage.getInputStream(), objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
        return profileImagePath;
    }

    @Override
    public UserDto getUserInfo(Long userId) throws UserNotFoundException {

        UserDo userDo = accountRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException());
        UserDto userDto = UserDto.builder()
                .userId(userDo.getUserId())
                .nickname(userDo.getNickname())
                .role(userDo.getRole())
                .birthday(userDo.getBirthday())
                .build();

        return userDto;
    }

    @Transactional
    @Override
    public void updatePassword(Long userId, String currentPassword, String newPassword)
            throws UserNotFoundException, IncorrectPasswordException {
        UserDo userDo = accountRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(currentPassword, userDo.getPassword())) {
            throw new IncorrectPasswordException();
        }
        String encodedPw = passwordEncoder.encode(newPassword);
        userDo.changePassword(encodedPw);
    }
}
