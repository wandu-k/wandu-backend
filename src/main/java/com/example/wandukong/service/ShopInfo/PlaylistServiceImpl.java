package com.example.wandukong.service.ShopInfo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.UserDto;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.example.wandukong.dto.ScrollDto.SliceResponseDto;
import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.BuyItemDto;
import com.example.wandukong.dto.ShopInfo.PlaylistAllDto;
import com.example.wandukong.dto.ShopInfo.PlaylistDto;
import com.example.wandukong.repository.ShopInfo.PlaylistAllpageRepository;

import jakarta.transaction.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  @Autowired
  PlaylistAllpageRepository playlistAllpageRepository;

  @Transactional
  @Override
  public SliceResponseDto<PlaylistAllDto> getAllplaylist(SliceRequestDto sliceRequestDto, UserDto userDto) {
    Pageable Pageable = sliceRequestDto.of();

    List<BgmList> bgmListPage;

    if (sliceRequestDto.getLastId() == null) {
      bgmListPage = playlistAllpageRepository.findAllByBgmListsAndBuyItemAndPlaylist(sliceRequestDto);
    } else {
      bgmListPage = playlistAllpageRepository.findAllByBgmListsAndBuyItemAndPlaylist(sliceRequestDto);
    }

    List<PlaylistAllDto> playlistAllDtos = bgmListPage.getContent().stream().map(bgmList -> {
      String itemNickname = bgmList.getBuyItem().getShop().getArtist();
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
          .userId(playlist.getUserDo().getUserId)
          .build();

      BuyItem buyItem = bgmList.getBuyItem();
      BuyItemDto buyItemDto = BuyItemDto.builder()
          .itemBuyId(buyItem.getItemBuyId())
          .buyDate(buyItem.getBuyDate())
          .itemId(buyItem.getShop().getItemId())
          .userId(buyItem.getUserDo().getUserId())
          .build();

      return PlaylistAllDto.builder()
          .build();
    }).collect(Collectors.toList());

    boolean hasNext = playlistAllDtos.size() == sliceRequestDto.getLimit();

    SliceResponseDto<PlaylistAllDto> responseDto = SliceResponseDto.<PlaylistAllDto>withAll()
        .dtoList(playlistAllDtos)
        .hasMoreData(hasNext)
        .build();

    return responseDto;
  }

}
