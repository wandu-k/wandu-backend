package com.example.wandukong.service.ShopInfo;

import java.util.List;
import java.util.ArrayList;

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
  PlaylistRepository playlistRepository;

  @Override
  public List<PlaylistDto> getAllplaylist(Long userId) {

    List<Playlist> playlists = playlistRepository.findAllByUserDo_UserId(userId);

    List<PlaylistDto> playlistDtos = new ArrayList<>();

    for (Playlist playlist : playlists) {

      PlaylistDto playlistDto = PlaylistDto.builder()
          .playlistId(playlist.getPlaylistId())
          .plName(playlist.getPlName())
          .userId(playlist.getPlaylistId())
          .plDate(playlist.getPlDate())
          .build();

      playlistDtos.add(playlistDto);
    }

    // Pageable pageable = sliceRequestDto.of();

    // Page<BgmList> bgmListPage;

    // if (sliceRequestDto.getLastId() == null) {
    // bgmListPage =
    // playlistAllpageRepository.findAllByBgmListsAndBuyItemAndPlaylist(sliceRequestDto,
    // userId);
    // } else {
    // bgmListPage =
    // playlistAllpageRepository.findAllByBgmListsAndBuyItemAndPlaylist(sliceRequestDto,
    // sliceRequestDto.getLastId(),
    // userId);
    // }

    // List<PlaylistAllDto> dtoList = bgmListPage.getContent().stream().map(bgmList
    // -> {
    // String playNickname = bgmList.getPlaylist().getUserDo().getNickname();

    // BgmListDto bgmListDto = BgmListDto.builder()
    // .bgmListId(bgmList.getBgmListId())
    // .playlistId(bgmList.getPlaylist().getPlaylistId())
    // .itemBuyId(bgmList.getBuyItem().getItemId())
    // .build();

    // Playlist playlist = bgmList.getPlaylist();
    // PlaylistDto playlistDto = PlaylistDto.builder()
    // .playlistId(playlist.getPlaylistId())
    // .plName(playlist.getPlName())
    // .userId(playlist.getUserDo().getUserId())
    // .build();

    // BuyItem buyItem = bgmList.getBuyItem();
    // BuyItemDto buyItemDto = BuyItemDto.builder()
    // .itemId(buyItem.getItemId())
    // .buyDate(buyItem.getBuyDate())
    // .itemId(buyItem.getShop().getItemId())
    // .userId(buyItem.getUserDo().getUserId())
    // .build();

    // Shop shop = buyItem.getShop();
    // ShopDto shopDto = ShopDto.builder()
    // .itemId(shop.getItemId())
    // .itemName(shop.getItemName())
    // .subcategoryId(shop.getShopSubcategory().getSubcategoryId())
    // .build();

    // ItemFile itemFile = shop.getItemFile();
    // ItemFileDto itemFileDto = ItemFileDto.builder()
    // .itemId(shop.getItemId())
    // .fileName(itemFile.getFileName())
    // .build();

    // // ShopInfoDto shopInfoDto = ShopInfoDto.builder()
    // // .shopDto(shopDto)
    // // .itemFileDto(itemFileDto)
    // // .build();

    // return PlaylistAllDto.builder()
    // .userDto(userDto)
    // .bgmListDto(bgmListDto)
    // .buyItemDto(buyItemDto)
    // .playlistDto(playlistDto)

    // .build();
    // }).collect(Collectors.toList());

    // boolean hasNext = dtoList.size() == sliceRequestDto.getLimit();

    // SliceResponseDto<PlaylistAllDto> responseDto =
    // SliceResponseDto.<PlaylistAllDto>withAll()
    // .dtoList(dtoList)
    // .hasMoreData(hasNext)
    // .sliceRequestDto(sliceRequestDto)
    // .build();

    return playlistDtos;
  }

  @Transactional
  @Override
  public void putMyPlaylist(PlaylistDto playlistDto, Long playlistId, Long userId) {

    Playlist playlist = playlistRepository.findByPlaylistIdAndUserDo_UserId(playlistId, userId);

    playlist.updatePlaylist(playlistDto.getPlName());
  }

  // 플레이어 생성
  @Override
  public void addPlayList(Long userId, PlaylistDto playlistDto) {
    Playlist playlist = Playlist.builder()
        .userDo(UserDo.builder()
            .userId(userId).build())
        .plName(playlistDto.getPlName())
        .build();

    playlistRepository.save(playlist);
  }

  @Override
  public void deleteMyPlaylist(PlaylistDto playlistDto, Long playlistId, Long userId) {
    playlistRepository.deleteByPlaylistIdAndUserDo_userId(playlistId, userId);
  }
}
