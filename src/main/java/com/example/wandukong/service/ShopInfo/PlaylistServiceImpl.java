package com.example.wandukong.service.ShopInfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.UserDo;
import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.ItemFile;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.domain.ShopInfo.Shop;
import com.example.wandukong.dto.AccountDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ItemFileDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.exception.CustomException.BadRequestException;
import com.example.wandukong.exception.CustomException.BgmListNotFoundException;
import com.example.wandukong.model.ApiResponseDto;
import com.example.wandukong.repository.AccountRepository;
import com.example.wandukong.repository.ShopInfo.BgmListRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.PlaylistAllpageRepository;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PlaylistServiceImpl implements PlaylistService {

  @Autowired
  PlaylistAllpageRepository playlistAllpageRepository;

  @Autowired
  AccountRepository accountRepository;

  @Autowired
  BuyItemRepository buyItemRepository;

  @Autowired
  BgmListRepository bgmListRepository;

  @Autowired
  PlaylistRepository playlistRepository;

  @Transactional
  @Override
  public SliceResponseDto<PlaylistAllDto> getAllplaylist(SliceRequestDto sliceRequestDto, Long userId) {

    UserDo userDo = accountRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException(null));

    AccountDto userDto = AccountDto.builder()
        .userId(userDo.getUserId())
        .build();

    Pageable pageable = sliceRequestDto.of();

    Page<BgmList> bgmListPage;

    if (sliceRequestDto.getLastId() == null) {
      bgmListPage = playlistAllpageRepository.findAllByBgmListsAndBuyItemAndPlaylist(sliceRequestDto, userId);
    } else {
      bgmListPage = playlistAllpageRepository.findAllByBgmListsAndBuyItemAndPlaylist(sliceRequestDto,
          sliceRequestDto.getLastId(),
          userId);
    }

    List<PlaylistAllDto> dtoList = bgmListPage.getContent().stream().map(bgmList -> {
      String playNickname = bgmList.getPlaylist().getUserDo().getNickname();

      BgmListDto bgmListDto = BgmListDto.builder()
          .bgmListId(bgmList.getBgmListId())
          .playlistId(bgmList.getPlaylist().getPlaylistId())
          .itemBuyId(bgmList.getBuyItem().getItemBuyId())
          .build();

      Playlist playlist = bgmList.getPlaylist();
      PlaylistDto playlistDto = PlaylistDto.builder()
          .playlistId(playlist.getPlaylistId())
          .plName(playlist.getPlName())
          .userId(playlist.getUserDo().getUserId())
          .build();

      BuyItem buyItem = bgmList.getBuyItem();
      BuyItemDto buyItemDto = BuyItemDto.builder()
          .itemBuyId(buyItem.getItemBuyId())
          .buyDate(buyItem.getBuyDate())
          .itemId(buyItem.getShop().getItemId())
          .userId(buyItem.getUserDo().getUserId())
          .build();

      Shop shop = buyItem.getShop();
      ShopDto shopDto = ShopDto.builder()
          .itemId(shop.getItemId())
          .itemName(shop.getItemName())
          .subcategoryId(shop.getShopSubcategory().getSubcategoryId())
          .build();

      ItemFile itemFile = shop.getItemFile();
      ItemFileDto itemFileDto = ItemFileDto.builder()
          .itemId(shop.getItemId())
          .fileName(itemFile.getFileName())
          .build();

      // ShopInfoDto shopInfoDto = ShopInfoDto.builder()
      // .shopDto(shopDto)
      // .itemFileDto(itemFileDto)
      // .build();

      return PlaylistAllDto.builder()
          .userDto(userDto)
          .bgmListDto(bgmListDto)
          .buyItemDto(buyItemDto)
          .playlistDto(playlistDto)

          .build();
    }).collect(Collectors.toList());

    boolean hasNext = dtoList.size() == sliceRequestDto.getLimit();

    SliceResponseDto<PlaylistAllDto> responseDto = SliceResponseDto.<PlaylistAllDto>withAll()
        .dtoList(dtoList)
        .hasMoreData(hasNext)
        .sliceRequestDto(sliceRequestDto)
        .build();

    return responseDto;
  }

  @Transactional
  @Override
  public ApiResponseDto putMyPlaylist(PlaylistDto playlistDto, BuyItemDto buyItemDto)
      throws BgmListNotFoundException, BadRequestException {

    Long playlistId = playlistDto.getPlaylistId();

    // 해당 플리가 존재할 경우 해당 번호의 플리 조회
    if (playlistId != null) {

      Playlist playlist = playlistRepository.findById(playlistId).orElse(null);

      BgmList bgmList = bgmListRepository.findById(playlist.getPlaylistId()).orElse(null);

      BuyItem buyItem = buyItemRepository.findById(bgmList.getBgmListId()).orElse(null);

      // bgmList 삭제
      bgmListRepository.deleteById(bgmList.getBgmListId());

      playlist.updatePlaylist(playlistDto.getPlName());
      // bgmList 재생성
      createBgmList(playlistDto, buyItemDto);

      ApiResponseDto apiResponseDto = ApiResponseDto.builder()
          .message("수정완료")
          .status(HttpStatus.OK)
          .build();
      return apiResponseDto;
    } else {
      log.info("플리가 없습니다. 플리 등록을 시작합니다.");

      Playlist newplaylist = Playlist.builder()
          .userDo(UserDo.builder().userId(playlistDto.getUserId()).build())
          .plName(playlistDto.getPlName())
          .build();
      playlistRepository.save(newplaylist);

      createBgmList(playlistDto, buyItemDto);

      ApiResponseDto apiResponse = ApiResponseDto.builder()
          .message("플레이리스트 등록이 완료되었습니다.")
          .status(HttpStatus.OK)
          .build();

      return apiResponse;
    }
  }

  // bgmList생성 메소드
  public void createBgmList(PlaylistDto playlistDto, BuyItemDto buyItemDto) {
    Long playlistId = playlistDto.getPlaylistId();
    Long itemBuyId = buyItemDto.getItemBuyId();

    // Playlist와 BuyItem 조회
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new UsernameNotFoundException("Playlist not found"));
    BuyItem buyItem = buyItemRepository.findById(itemBuyId)
        .orElseThrow(() -> new UsernameNotFoundException("BuyItem not found"));

    // BuyItem의 Shop의 CategoryId가 1일 경우에만 BgmList 생성 및 저장
    if (buyItem.getShop().getShopSubcategory().getSubcategoryId() == 1) {
      BgmList newBgmList = BgmList.builder()
          .playlist(playlist)
          .buyItem(buyItem)
          .build();
      bgmListRepository.save(newBgmList);
    }
  }

}
