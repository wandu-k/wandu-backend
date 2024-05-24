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
import com.example.wandukong.dto.PageRequestDto;
import com.example.wandukong.dto.PageResponseDto;
import com.example.wandukong.dto.ShopInfo.BuyItemAllDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ItemFileDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.UserNotFoundException;
import com.example.wandukong.repository.AccountRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemPageRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.CategoryRepository;
import com.example.wandukong.repository.ShopInfo.ShopInfoRepository;

import jakarta.transaction.Transactional;

@Service
public class BuyItemServiceimpl implements BuyItemService {

        @Autowired
        private AccountRepository userRepository;

        @Autowired
        private ShopInfoRepository shopRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private BuyItemRepository buyItemRepository;

        @Autowired
        private BuyItemPageRepository buyItemPageRepository;

        @Autowired
        private AmazonS3 amazonS3;

        @Transactional
        @Override
        public PageResponseDto<BuyItemAllDto> getMybuylist(PageRequestDto pageRequestDto, AccountDto userDto)
                        throws UserNotFoundException {
                // 사용자 정보 조회
                UserDo user = userRepository.findById(userDto.getUserId())
                                .orElseThrow(() -> new UserNotFoundException());

                // jpa를 사용하여 페이지 별로 사용자가 구매 내역 정보를 가져옴
                Page<BuyItem> buyItemPage = buyItemPageRepository.findByUserDoUserId(user, pageRequestDto);

                // 가져온 구매 내역 정보를 BuyItemDto로 변환
                List<BuyItemAllDto> buyitemList = buyItemPage.getContent().stream().map(buyitem -> {

                        String nickName = buyitem.getShop().getUserDo().getNickname();

                        BuyItemDto buyItemDto = BuyItemDto.builder()
                                        .itemBuyId(buyitem.getItemBuyId())
                                        .buyDate(buyitem.getBuyDate())
                                        .itemId(buyitem.getShop().getItemId())
                                        .userId(buyitem.getUserDo().getUserId())
                                        .build();
                        // ShopDto를 생성하여 설정
                        ShopDto shopDto = ShopDto.builder()
                                        .itemId(buyitem.getShop().getItemId())
                                        .itemName(buyitem.getShop().getItemName())
                                        .categoryId(buyitem.getShop().getCategory().getCategoryId())
                                        .build();

                        ItemFile itemFile = buyitem.getShop().getItemFile();

                        ItemFileDto itemFileDto = ItemFileDto.builder()
                                        .itemId(buyitem.getShop().getItemFile().getItemId())
                                        .fileName(itemFile.getFileName())
                                        .build();

                        ShopInfoDto shopInfoDto = ShopInfoDto.builder()
                                        .shopDto(shopDto)
                                        .nickName(nickName)
                                        .itemFileDto(itemFileDto)
                                        .build();

                        // BuyItemAllDto를 생성하여 반환-구매내역과 그에 해당하는 상점 정보를 가져오기위함
                        return BuyItemAllDto.builder()
                                        .buyItemDto(buyItemDto)
                                        .shopInfoDto(shopInfoDto)
                                        .build();
                }).collect(Collectors.toList());

                // 반환된 목록을 포함하는 PageResponseDto를 생성
                PageResponseDto<BuyItemAllDto> responseDto = PageResponseDto.<BuyItemAllDto>withAll()
                                .dtoList(buyitemList)
                                .pageRequestDto(pageRequestDto)
                                .total(buyItemPage.getTotalElements())
                                .build();
                return responseDto;

        }

        @Transactional
        @Override
        public void purchaseItem(ShopInfoDto shopInfoDto, AccountDto userDto) throws UserNotFoundException {
                // 현재 사용자 정보 조회
                UserDo user = userRepository.findById(userDto.getUserId())
                                .orElseThrow(() -> new UserNotFoundException());

                // 구매할 아이템 정보 조회
                Shop shop = shopRepository.findById(shopInfoDto.getShopDto().getItemId())
                                .orElseThrow(() -> new IllegalArgumentException());

                // BuyItem 엔티티 생성 및 저장
                BuyItem buyItem = BuyItem.builder()
                                .buyDate(new Date())
                                .userDo(user)
                                .shop(shop)
                                .build();

                // 사용자의 포인트 차감 및 업데이트
                Long totalPrice = shop.getPrice();
                Long updatedPoint = user.getPoint() - totalPrice;
                userDto.setPoint(updatedPoint);
                userRepository.update(user);

                buyItemRepository.save(buyItem);
        }

}
