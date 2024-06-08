package com.example.wandukong.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.repository.ShopInfo.BgmListRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BgmServiceImpl implements BgmService {

    private final BuyItemRepository buyItemRepository;
    private final PlaylistRepository playlistRepository;
    private final BgmListRepository bgmListRepository;

    @Override
    public void addBgm(BgmListDto bgmListDto) {

        BuyItem buyItem = buyItemRepository.getReferenceById(bgmListDto.getItemId());
        Playlist playlist = playlistRepository.getReferenceById(bgmListDto.getPlaylistId());

        // BuyItem의 Shop의 CategoryId가(음악은 2) 2일 경우에만 BgmList 생성 및 저장
        // 음악 카테고리는 2번이고 서브카테고리가 아니라 메인 카테고리.
        if (buyItem.getShop().getShopSubcategory().getCategory().getCategoryId() == 2) {
            BgmList newBgmList = BgmList.builder()
                    .playlist(playlist)
                    .buyItem(buyItem)
                    .build();
            bgmListRepository.save(newBgmList);
        }
    }

    @Override
    public List<ShopInfoDto> getBgmList(Long playlistId) {

        List<ShopInfoDto> shopInfoDto = bgmListRepository.findAllByPlaylistId(playlistId);

        return shopInfoDto;
    }

}
