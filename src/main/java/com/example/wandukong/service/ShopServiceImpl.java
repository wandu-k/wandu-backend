package com.example.wandukong.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.ItemFileDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.itemUploadNotFoundException;
import com.example.wandukong.repository.ShopInfo.CategoryRepository;
import com.example.wandukong.repository.ShopInfo.ItemFileRepository;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

  @Autowired
  ShopInfoRepository shopInfoRepository;

  @Autowired
  ItemFileRepository itemFileRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  AmazonS3 amazonS3;

  @Value(value = "${cloud.aws.s3.bucket}")
  private String bucketName;

  // 아이템 등록
  @Transactional
  @Override
  public void putPost(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
      throws itemUploadNotFoundException, IOException {

    if (customUserDetails != null) {

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

      shopInfoRepository.save(shop);
      categoryRepository.save(category);

      if (shopInfoDto.getItemFileDto().getFileName().isEmpty()) {

        itemfileUpload(itemfile, shopInfoDto, customUserDetails);

      }
      log.info("각 아이템 정보들" + shopInfoDto);

    } else {
      throw new itemUploadNotFoundException();
    }

  }

  // 아이템 정보 업데이트
  @Transactional
  @Override
  public void updateItemFile(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
      throws itemUploadNotFoundException, IOException {

    if (customUserDetails != null) {
      Shop shop = shopInfoRepository.findByItemID(shopInfoDto.getShopDto().getItemID());

      itemfileUpload(itemfile, shopInfoDto, customUserDetails);

      shop.updateItem(shopInfoDto.getShopDto().getItemName());
    } else {
      throw new itemUploadNotFoundException();
    }

  }

  public String itemfileUpload(MultipartFile itemfile, ShopInfoDto shopInfoDto, CustomUserDetails customUserDetails)
      throws IOException {
    // 아이템 파일 업로드

    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentType(itemfile.getContentType());

    // 파일 경로 지정()
    String filepath = "shop/" + customUserDetails.getUserDto().getNickname() + "/"
        + shopInfoDto.getCategoryDto().getCategoryName() + "/";

    // uuid설정
    String uuID = UUID.randomUUID().toString();
    shopInfoDto.getItemFileDto().setUuid(uuID);

    // 아이템 명을 파일 이름으로 저장
    shopInfoDto.getItemFileDto().setFileName(shopInfoDto.getShopDto().getItemName());

    // 확장자 구분
    String extension = itemfile.getOriginalFilename().substring(itemfile.getOriginalFilename().lastIndexOf('.'));

    String filename = shopInfoDto.getItemFileDto().getUuid() + shopInfoDto.getItemFileDto().getFileName() + extension;

    String itemfilepath = filepath + filename;

    amazonS3
        .putObject(new PutObjectRequest(bucketName, itemfilepath,
            itemfile.getInputStream(), objectMetadata)
            .withCannedAcl(CannedAccessControlList.PublicRead));

    // ItemFile 엔티티 생성 및 저장
    ItemFile itemFile = ItemFile.builder()
        .uuid(shopInfoDto.getItemFileDto().getUuid())
        .fileName(shopInfoDto.getItemFileDto().getFileName())
        .build();

    itemFileRepository.save(itemFile);

    return itemfilepath;
  }

  @Transactional
  @Override
  public PageResponseDto<ShopInfoDto> getShopitemList(PageRequestDto pageRequestDto) {

    // JPA를 사용하여 페이지 단위로 상점 정보를 가져옴
    Page<Shop> shopPage = shopInfoRepository.findAllByCategoryAndItemFileIsNotNull(pageRequestDto);

    // 가져온 상점 정보를 ShopInfoDto로 변환
    List<ShopInfoDto> dtoList = shopPage.getContent().stream()
        .map(shop -> {
          // 유저의 닉네임을 가져옴
          String nickName = shop.getUserDo().getNickname();

          // ShopDto를 생성하여 설정
          ShopDto shopDto = ShopDto.builder()
              .itemID(shop.getItemID())
              .itemName(shop.getItemName())
              .categoryID(shop.getCategory().getCategoryID())
              .build();

          // ItemFile 엔티티를 가져와서 ItemFileDto로 변환하여 설정
          ItemFile itemFile = shop.getItemFile(); // ItemFile 엔티티 가져오기
          ItemFileDto itemFileDto = null;
          if (itemFile != null) { // ItemFile이 존재하는 경우에만 처리
            itemFileDto = ItemFileDto.builder()
                .uuid(itemFile.getUuid())
                .fileName(itemFile.getFileName())
                .build();
          }

          // ShopInfoDto를 생성하여 반환
          return ShopInfoDto.builder()
              .shopDto(shopDto)
              .nickName(nickName) // 닉네임 설정
              .itemFileDto(itemFileDto) // 아이템 파일 정보 설정
              .build();
        }).collect(Collectors.toList());

    // 변환된 목록을 포함하는 PageResponseDto를 생성
    PageResponseDto<ShopInfoDto> responseDto = PageResponseDto.<ShopInfoDto>withAll()
        .dtoList(dtoList) // 변환된 목록 설정
        .pageRequestDto(pageRequestDto) // 페이지 요청 설정
        .total(shopPage.getTotalElements()) // 전체 상점 수 설정
        .build();

    return responseDto; // 생성된 PageResponseDto 반환
  }

  @Override
  public PageResponseDto<ShopInfoDto> getMyitemUploadList(PageRequestDto pageRequestDto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getMyitemUploadList'");
  }

}