package com.example.wandukong.service.ShopInfo;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.ItemFile;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.InventoryItemDto;
import com.example.wandukong.dto.SearchItemDto;
import com.example.wandukong.dto.page.PageRequestDto;
import com.example.wandukong.dto.page.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ItemFileDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.EntityAlreadyExistsException;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.repository.AccountRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemPageRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;
import jakarta.transaction.Transactional;

@Service
public class InventoryItemServiceimpl implements InventoryItemService {

        @Autowired
        private BuyItemRepository buyItemRepository;

        @Autowired
        private BuyItemPageRepository buyItemPageRepository;

        @Override
        public PageResponseDto<InventoryItemDto> getMybuylist(Long userId, SearchItemDto searchItemDto) {

                // jpa를 사용하여 페이지 별로 사용자가 구매 내역 정보를 가져옴
                Page<InventoryItemDto> buyItemPage = buyItemPageRepository.findByUserDoUserId(userId, searchItemDto);

                // 가져온 구매 내역 정보를 BuyItemDto로 변환
                List<InventoryItemDto> buyitemList = buyItemPage.getContent();

                // 반환된 목록을 포함하는 PageResponseDto를 생성
                PageResponseDto<InventoryItemDto> responseDto = PageResponseDto.<InventoryItemDto>withAll()
                                .dtoList(buyitemList)
                                .pageRequestDto(new PageRequestDto(searchItemDto.getCategoryId(),
                                                searchItemDto.getPage(),
                                                searchItemDto.getSize()))
                                .total(buyItemPage.getTotalElements())
                                .build();
                return responseDto;
        }

        @Override
        public void addItem(BuyItemDto buyItemDto) throws EntityAlreadyExistsException {

                BuyItem buyItem = buyItemRepository.findByShop_ItemId(buyItemDto.getItemId());

                if (buyItem != null) {
                        throw new EntityAlreadyExistsException();
                }

                buyItem = buyItemDto.toEntity();

                buyItemRepository.save(buyItem);
        }

}
