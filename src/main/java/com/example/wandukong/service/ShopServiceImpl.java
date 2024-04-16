package com.example.wandukong.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.Category;
import com.example.wandukong.domain.ShopInfo.ItemFile;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.itemUploadNotFoundException;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

  @Autowired
  ShopInfoRepository shopInfoRepository;

  @Autowired
  AmazonS3 amazonS3;

  @Value(value = "${cloud.aws.s3.bucket}")
  private String bucketName;

  // 아이템 등록
  @Transactional
  @Override
  public String putPost(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
      throws itemUploadNotFoundException {

    // 아이템 카테고리 설정: ex)BGM, avatar 등등
    Category category = Category.builder()
        .categoryName(shopInfoDto.getCategoryDto().getCategoryName())
        .build();

    // 등록할 아이템 정보 설정
    Shop shop = Shop.builder()
        .itemName(shopInfoDto.getShopDto().getItemName())
        .userDo(UserDo.builder().userID(customUserDetails.getUserDto().getUserID()).build())
        .category(Category.builder().categoryID(shopInfoDto.getCategoryDto().getCategoryID()).build())
        .build();

    log.info("각 아이템 정보들" + shopInfoDto);
    // 아이템 파일 업로드
    try {
      ObjectMetadata objectMetadata = new ObjectMetadata();
      objectMetadata.setContentType(itemfile.getContentType());

      // 파일 경로 지정()
      String filepath = "shop/" + customUserDetails.getUserDto().getNickname() + "/" + category.getCategoryName() + "/";

      // uuid설정
      String uuID = UUID.randomUUID().toString();
      shopInfoDto.getItemFileDto().setUuid(uuID);

      // 확장자 구분
      String extension = itemfile.getOriginalFilename().substring(itemfile.getOriginalFilename().lastIndexOf('.'));

      // 아이템 명을 파일 이름으로 저장
      shopInfoDto.getItemFileDto().setFileName(shopInfoDto.getShopDto().getItemName());

      String filename = shopInfoDto.getItemFileDto().getUuid() + shopInfoDto.getItemFileDto().getFileName() + extension;

      String itemfilepath = filepath + filename;

      ItemFile itemFile = ItemFile.builder()
          .fileName(shopInfoDto.getItemFileDto().getFileName())
          .uuid(shopInfoDto.getItemFileDto().getUuid())
          .build();

      amazonS3
          .putObject(new PutObjectRequest(bucketName, itemfilepath,
              itemfile.getInputStream(), objectMetadata)
              .withCannedAcl(CannedAccessControlList.PublicRead));

      shopInfoRepository.save(shop, category, itemFile);

      return itemfilepath;

    } catch (Exception e) {
      log.error("파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
      throw new itemUploadNotFoundException("파일 업로드 중 오류가 발생했습니다.");
    }

  }

  @Override
  public void updateItemFile(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
      throws itemUploadNotFoundException {

  }

  @Override
  public List<ShopInfoDto> getShopitemList() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getShopitemList'");
  }
}