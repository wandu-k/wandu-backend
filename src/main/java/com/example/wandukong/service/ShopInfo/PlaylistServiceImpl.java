package com.example.wandukong.service.ShopInfo;

import java.util.List;
import java.util.Optional;
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
import com.example.wandukong.dto.CustomUserDetails;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.ItemFileDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.dto.ShopInfo.ShopDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.exception.CustomException.BgmListNotFoundException;
import com.example.wandukong.model.ApiResponse;
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
  CustomUserDetails customUserDetails;

  @Autowired
  BuyItemRepository buyItemRepository;

  @Autowired
  BgmListRepository bgmListRepository;

  @Autowired
  PlaylistRepository playlistRepository;

  @Autowired
  UserDto userdto;

  @Transactional
  @Override
  public SliceResponseDto<PlaylistAllDto> getAllplaylist(SliceRequestDto sliceRequestDto, Long userId) {

    UserDo userDo = accountRepository.findById(userId)
        .orElseThrow(() -> new UsernameNotFoundException(null));

    UserDto userDto = UserDto.builder()
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
          .categoryId(shop.getCategory().getCategoryId())
          .artist(shop.getArtist())
          .build();

      ItemFile itemFile = shop.getItemFile();
      ItemFileDto itemFileDto = ItemFileDto.builder()
          .itemId(shop.getItemId())
          .fileName(itemFile.getFileName())
          .build();

      ShopInfoDto shopInfoDto = ShopInfoDto.builder()
          .shopDto(shopDto)
          .itemFileDto(itemFileDto)
          .build();

      return PlaylistAllDto.builder()
          .userDto(userDto)
          .bgmListDto(bgmListDto)
          .buyItemDto(buyItemDto)
          .playlistDto(playlistDto)
          .shopInfoDto(shopInfoDto)
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
  public ApiResponse updateMyPlaylist(PlaylistAllDto playlistAllDto) throws BgmListNotFoundException {

    Long bgmListId = playlistAllDto.getBgmListDto().getBgmListId();
    Long playlistId = playlistAllDto.getPlaylistDto().getPlaylistId();
    Long itemBuyId = playlistAllDto.getBuyItemDto().getItemBuyId();

    Optional<BgmList> optionalBgmList = bgmListRepository.findById(bgmListId);

    Optional<Playlist> optionalPlaylist = playlistRepository.findById(playlistId);

    Optional<BuyItem> optionalBuyItem = buyItemRepository.findById(itemBuyId);

    if (optionalPlaylist.isPresent()) {
      log.info("플리가 이미 있습니다. 플리 수정으로 넘어갑니다.");

      optionalBgmList.get().updatePost(bgmListId, playlistAllDto.getPlaylistDto().getPlName(),
          playlistAllDto.getPlaylistDto().getPlDate(), itemBuyId);

      ApiResponse apiResponse = ApiResponse.builder()
          .message("플레이리스트 수정이 완료되었습니다.")
          .status(HttpStatus.OK)
          .build();

      return apiResponse;
    } else {
      log.info("플리가 없습니다. 플리 등록을 시작합니다.");

      Playlist newplaylist = Playlist.builder()
          .userDo(UserDo.builder().userId(playlistAllDto.getUserDto().getUserId()).build())
          .plName(playlistAllDto.getPlaylistDto().getPlName())
          .build();
      playlistRepository.save(newplaylist);

      deleteBgmList(playlistAllDto);

      createBgmList(playlistAllDto);

      ApiResponse apiResponse = ApiResponse.builder()
          .message("플레이리스트 등록이 완료되었습니다.")
          .status(HttpStatus.OK)
          .build();

      return apiResponse;
    }
  }

  // bgmList 삭제메소드
  public void deleteBgmList(PlaylistAllDto playlistAllDto) throws BgmListNotFoundException {
    Long bgmListId = playlistAllDto.getBgmListDto().getBgmListId();
    Long playlistId = playlistAllDto.getPlaylistDto().getPlaylistId();

    BgmList bgmList = bgmListRepository.findById(bgmListId).orElseThrow(() -> new BgmListNotFoundException());

    if (bgmList.getPlaylist().getPlaylistId().equals(playlistId)) {
      bgmListRepository.deleteById(bgmListId);
    }
  }

  // bgmList생성 메소드
  public void createBgmList(PlaylistAllDto playlistAllDto) {
    Long playlistId = playlistAllDto.getPlaylistDto().getPlaylistId();
    Long itemBuyId = playlistAllDto.getBuyItemDto().getItemBuyId();

    // Playlist와 BuyItem 조회
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new UsernameNotFoundException("Playlist not found"));
    BuyItem buyItem = buyItemRepository.findById(itemBuyId)
        .orElseThrow(() -> new UsernameNotFoundException("BuyItem not found"));

    // BuyItem의 Shop의 CategoryId가 1일 경우에만 BgmList 생성 및 저장
    if (buyItem.getShop().getCategory().getCategoryId() == 1) {
      BgmList newBgmList = BgmList.builder()
          .playlist(playlist)
          .buyItem(buyItem)
          .build();
      bgmListRepository.save(newBgmList);
    }
  }

}
