package com.example.wandukong.service.ShopInfo;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.wandukong.domain.ShopInfo.ItemFile;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.ShopInfo.CategoryRepository;
import com.example.wandukong.repository.ShopInfo.ItemFileRepository;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;
import com.example.wandukong.repository.user.UserRepository;
import com.example.wandukong.util.S3Util;

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

  private final S3Util s3Util;

  @Value(value = "${cloud.aws.s3.bucket}")
  private String bucketName;

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

  @Transactional
  @Override
  public ApiResponseDto putItem(MultipartFile itemfile, ShopDto shopDto) throws IOException, BadRequestException {

    if (itemfile == null) {
      throw new BadRequestException();
    }

    // Dto 아이템 번호 존재 여부 확인
    if (shopDto.getItemId() != null) {

      // 만약 존재한다면 해당 번호의 아이템 조회
      Shop shop = shopInfoRepository.findById(shopDto.getItemId()).orElse(null);

      ItemFile itemFile = itemFileRepository.findById(shop.getItemId()).orElse(null);

      // 기존 아이템 이미지 삭제
      s3Util.itemfileDelete(shop.getItemFile().getFileName());

      // 오프벡트 키 생성
      String objectKey = filePathGenerate(itemfile, shop);

      // 파일 업로드
      s3Util.itemfileUpload(itemfile, objectKey);

      // 상점 아이템 및 파일 정보 수정
      shop.updateItem(shopDto.getItemName(), shopDto.getPrice());
      itemFile.changeFileName(objectKey);

      ApiResponseDto apiResponseDto = ApiResponseDto.builder()
          .message("수정 완료")
          .status(HttpStatus.OK)
          .build();

      return apiResponseDto;

    }

    // 등록할 아이템 정보 설정
    Shop shop = shopDto.toEntity();

    shopInfoRepository.save(shop);

    String objectKey = filePathGenerate(itemfile, shop);

    s3Util.itemfileUpload(itemfile, objectKey);
    // ItemFile 엔티티 생성 및 저장
    ItemFile itemFile = ItemFile.builder()
        .itemId(shop.getItemId())
        .fileName(objectKey)
        .build();

    itemFileRepository.save(itemFile);
    log.info("각 아이템 정보들" + shop);

    ApiResponseDto apiResponseDto = ApiResponseDto.builder()
        .message("추가 완료")
        .status(HttpStatus.CREATED)
        .build();
    return apiResponseDto;
  }

  private String filePathGenerate(MultipartFile itemfile, Shop shop) {

    String ext = itemfile.getOriginalFilename().substring(itemfile.getOriginalFilename().lastIndexOf("."));
    // 파일 경로 지정()
    String filepath = "shop/" + shop.getShopSubcategory().getSubcategoryId() + "/"
        + shop.getUserDo().getUserId() + "/" + ext;

    // uuid설정
    String uuid = UUID.randomUUID().toString();

    String filename = uuid + "-" + shop.getItemName();

    String objectKey = filepath + filename;

    return objectKey;
  }

  @Override
  public ShopInfoDto getItem(Long itemId, Long userId) {
    ShopInfoDto shopInfoDto = shopInfoRepository.findByIdWithDto(itemId, userId);
    return shopInfoDto;
  }

}
