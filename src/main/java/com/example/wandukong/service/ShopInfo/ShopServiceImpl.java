package com.example.wandukong.service.ShopInfo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wandukong.domain.ShopInfo.ItemFile;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.repository.ShopInfo.CategoryRepository;
import com.example.wandukong.repository.ShopInfo.ItemFileRepository;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;
import com.example.wandukong.repository.user.UserRepository;
import com.example.wandukong.util.S3Util;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

  private final ShopInfoRepository shopInfoRepository;

  @Autowired
  ItemFileRepository itemFileRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  UserRepository accountRepository;

  private final AmazonS3 amazonS3;

  @Value(value = "${cloud.aws.s3.bucket}")
  private String bucketName;

  private final S3Util s3Util;

  @Override
  public PageResponseDto<ShopInfoDto> getShopItemList(SearchItemDto searchItemDto) {
    // JPA를 사용하여 페이지 단위로 상점 정보를 가져옴
    Page<ShopInfoDto> shopPage = shopInfoRepository.SearchAndfindAll(searchItemDto);

    List<ShopInfoDto> list = shopPage.getContent();

    return PageResponseDto.<ShopInfoDto>withAll()
        .dtoList(list)
        .pageRequestDto(PageRequestDto.builder().page(searchItemDto.getPage()).size(searchItemDto.getSize()).build())
        .total(shopPage.getTotalElements())
        .build();
  }

  private String filePathGenerate(MultipartFile itemfile, Shop shop) {

    String ext = itemfile.getOriginalFilename().substring(itemfile.getOriginalFilename().lastIndexOf("."));
    // 파일 경로 지정()
    String filepath = "shop/" + shop.getShopSubcategory().getSubcategoryId() + "/"
        + shop.getUserDo().getUserId() + "/";

    // uuid설정
    String uuid = UUID.randomUUID().toString();

    String filename = uuid + "-" + shop.getItemName() + ext;

    String objectKey = filepath + filename;

    return objectKey;
  }

  @Override
  public ShopInfoDto getItem(Long itemId) {
    ShopInfoDto shopInfoDto = shopInfoRepository.findByIdWithDto(itemId);
    return shopInfoDto;
  }

  @Transactional
  @Override
  public void addItem(MultipartFile itemfile, ShopDto shopDto) throws IOException {

    // 등록할 아이템 정보 설정
    Shop shop = shopDto.toEntity();

    shop = shopInfoRepository.save(shop);

    log.info("아이템 저장 완료");

    String objectKey = filePathGenerate(itemfile, shop);

    log.info("아이템 업로드 성공");

    ItemFile.ItemFileBuilder itemFileBuilder = ItemFile.builder()
        .itemId(shop.getItemId())
        .fileName(objectKey);

    File tempFile = File.createTempFile("upload", itemfile.getOriginalFilename());
    try (FileOutputStream fos = new FileOutputStream(tempFile)) {
      fos.write(itemfile.getBytes());
    }
    Path filePath = tempFile.toPath();

    String mimeType = Files.probeContentType(filePath);

    // 오디오 파일 MIME 타입 체크
    // 오디오 파일 MIME 타입 체크
    if (mimeType.startsWith("audio/")) {
      log.info("오디오 파일");
      Mp3File mp3File;
      try {
        mp3File = new Mp3File(filePath.toFile());
        if (mp3File.hasId3v2Tag()) {
          ID3v2 id3v2Tag = mp3File.getId3v2Tag();
          String title = id3v2Tag.getTitle();
          String artist = id3v2Tag.getArtist();
          String album = id3v2Tag.getAlbum();
          byte[] albumImage = id3v2Tag.getAlbumImage();

          // 메타데이터 출력 및 DTO 설정
          System.out.println("Title: " + title);
          System.out.println("Artist: " + artist);
          System.out.println("Album: " + album);

          // 앨범 아트가 존재하는 경우
          if (albumImage != null) {
            String uuid = UUID.randomUUID().toString();
            String thumbnail = "thumbnail/" + uuid + ".jpeg";

            // 바이트 배열을 ByteArrayInputStream으로 변환
            ByteArrayInputStream albumImageInputStream = new ByteArrayInputStream(albumImage);

            // ObjectMetadata 설정
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType("image/jpeg");
            objectMetadata.setContentLength(albumImage.length);

            amazonS3.putObject(new PutObjectRequest(bucketName, thumbnail,
                albumImageInputStream, objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));

            itemFileBuilder.thumbnail(thumbnail);

            // 임시 파일 삭제
            tempFile.delete();

          } else {
            System.out.println("No album art found");
          }

        } else {
          System.out.println("No ID3v2 tag found");
        }

      } catch (UnsupportedTagException | InvalidDataException | IOException e) {
        e.printStackTrace();
      }
    }

    ItemFile itemFile = itemFileBuilder.build();

    s3Util.itemfileUpload(itemfile, objectKey);

    itemFileRepository.save(itemFile);

  }
}
