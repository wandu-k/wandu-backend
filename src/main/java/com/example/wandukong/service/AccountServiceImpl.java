package com.example.wandukong.service;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.MiniHome.MiniHome;

import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.MiniHome.MiniHomeDto;
import com.example.wandukong.repository.AccountRepository;
import com.example.wandukong.repository.MiniHomeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    MiniHomeRepository miniHpRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AmazonS3 amazonS3;

    @Value(value = "${cloud.aws.s3.bucket}")
    private String bucketName;

    // 회원가입
    @Transactional
    @Override
    public int register(UserDto userDto) {
        String encodedPw = passwordEncoder.encode(userDto.getPassword());

        // Check for duplicate username before saving
        if (accountRepository.findByEmail(userDto.getEmail()) == null) {
            userDto.setPassword(encodedPw);
            UserDo user = userDto.toEntity();
            user = accountRepository.save(user);

            log.info("회원가입된 유저 아이디" + user.getUserID());

            // 회원가입이 완료되면 그 유저아이디로 미니홈도 생성
            MiniHomeDto miniHomeDto = new MiniHomeDto();
            miniHomeDto.setUserID(user.getUserID());
            MiniHome miniHome = miniHomeDto.toEntity();

            log.info("홈피 유저 아이디 : " + miniHome.getUserDo().getUserID());

            miniHpRepository.save(miniHome);
            return 0;
        }
        return 1;
    }

    @Override
    public void deleteAccount(Long userID) {
        accountRepository.deleteById(userID);
    }

    // 유저정보 업데이트
    @Transactional
    @Override
    public void updateProfile(MultipartFile profileImage, UserDto userDto) throws IOException {

        UserDo userDo = accountRepository.findById(userDto.getUserID()).orElseThrow(() -> {
            throw new IllegalArgumentException("해당하는 아이디가 없습니다");
        });

        if (profileImage != null) {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(profileImage.getContentType());

            String filePath = "users/" + userDto.getUserID() + "/profile/";

            String extension = profileImage.getOriginalFilename()
                    .substring(profileImage.getOriginalFilename().lastIndexOf('.'));

            String filename = "profile" + "_" + userDto.getUserID() + extension;

            amazonS3
                    .putObject(new PutObjectRequest(bucketName, filePath + filename,
                            profileImage.getInputStream(), objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            userDto.setProfileImage(filePath + filename);
        }

        userDo.updateProfile(userDto.getEmail(), userDto.getName(), userDto.getNickname(), userDto.getProfileImage(),
                userDto.getBirthday(), userDto.getPhone(), userDto.getGender());

    }

    @Override
    public UserDto getMyInfo(String username) {

        UserDo userDo = accountRepository.findByEmail(username);
        UserDto userDto = new UserDto();
        userDto.setUserID(userDo.getUserID());
        userDto.setEmail(userDo.getEmail());
        userDto.setProfileImage(userDo.getProfileImage());

        String objectKey = userDto.getProfileImage();

        // KMS 암호화 파일 주소 만들때 쓰는 코드

        // Date expiration = new Date(System.currentTimeMillis() + 3600000);
        // GeneratePresignedUrlRequest generatePresignedUrlRequest = new
        // GeneratePresignedUrlRequest(bucketName, objectKey)
        // .withMethod(HttpMethod.GET)
        // .withExpiration(expiration);

        // URL signedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
        userDto.setProfileImage(amazonS3.getUrl(bucketName, objectKey).toString());
        return userDto;
    }

    // 필요없는 부분

    // @Override
    // public void login(UserDto userDto, HttpServletRequest request) {
    // log.info(userDto.getEmail());
    // log.info(userDto.getPassword());

    // // 인증 토큰 생성
    // UsernamePasswordAuthenticationToken token = new
    // UsernamePasswordAuthenticationToken(userDto.getEmail(),
    // userDto.getPassword());

    // // 토큰에 요청정보 등록
    // token.setDetails(new WebAuthenticationDetails(request));

    // // 토큰을 이용하여 인증 요청 로그인
    // Authentication authentication = authenticationManager.authenticate(token);
    // log.info("인증 정보 : " + authentication.isAuthenticated());

    // User authUser = (User) authentication.getPrincipal();
    // log.info("인증된 사용자 : " + authUser.getUsername());

    // }
}
