package com.example.wandukong.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.BgmListPK;
import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;
import com.example.wandukong.repository.ShopInfo.BgmListRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BgmServiceImpl implements BgmService {

    private final BuyItemRepository buyItemRepository;
    private final PlaylistRepository playlistRepository;
    private final BgmListRepository bgmListRepository;

    @Override
    public void addBgm(Long playlistId, Long itemId) {

        BuyItem buyItem = buyItemRepository.getReferenceById(itemId);

        log.info("구매한 아이템 참조");

        Playlist playlist = playlistRepository.getReferenceById(playlistId);

        log.info("플레이리스트 참조");

        // BuyItem의 Shop의 CategoryId가(음악은 2) 2일 경우에만 BgmList 생성 및 저장
        // 음악 카테고리는 2번이고 서브카테고리가 아니라 메인 카테고리.
        if (buyItem.getShop().getShopSubcategory().getCategory().getCategoryId() == 2) {
            BgmListPK bgmListPK = new BgmListPK(buyItem, playlist);
            BgmList newBgmList = new BgmList(bgmListPK);
            bgmListRepository.save(newBgmList);
            log.info("플레이리스트 bgm 저장 완료 ");
        }

    }

    @Override
    public List<ShopInfoDto> getBgmList(Long playlistId) {

        List<ShopInfoDto> shopInfoDto = bgmListRepository.findAllByPlaylistId(playlistId);

        return shopInfoDto;
    }

    @Override
    public void deleteBgm(Long playlistId, Long itemId) {

        BuyItem buyItem = buyItemRepository.getReferenceById(itemId);
        Playlist playlist = playlistRepository.getReferenceById(playlistId);

        BgmListPK bgmListPK = new BgmListPK(buyItem, playlist);

        BgmList bgmList = new BgmList(bgmListPK);

        bgmListRepository.delete(bgmList);
    }

}
