package com.example.wandukong.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.BgmListPK;
import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.repository.ShopInfo.BgmListRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;
import com.example.wandukong.util.S3Util;

import jakarta.transaction.TransactionScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BgmServiceImpl implements BgmService {

    private final BuyItemRepository buyItemRepository;
    private final PlaylistRepository playlistRepository;
    private final BgmListRepository bgmListRepository;
    private final S3Util s3Util;

    @Transactional
    @Override
    public void addBgm(Long playlistId, Long buyItemId) {
        // 플레이리스트와 구매 항목 엔티티를 ID로 조회합니다.
        Playlist playlist = playlistRepository.getReferenceById(playlistId);
        BuyItem buyItem = buyItemRepository.getReferenceById(buyItemId);

        // 디버깅을 위해 플레이리스트와 구매 항목 ID를 로그에 기록합니다.
        log.info("플레이리스트: " + playlist + ", 구매 항목 ID: " + buyItemId);

        // 구매 항목의 상점 카테고리 ID가 2(음악 카테고리)인지 확인합니다.
        if (buyItem.getShop().getShopSubcategory().getCategory().getCategoryId() == 2) {
            // BgmList 엔티티의 기본 키를 생성합니다.
            BgmListPK bgmListPK = new BgmListPK(playlistId, buyItemId);

            // 생성한 기본 키와 함께 BgmList 엔티티를 생성합니다.
            BgmList bgmList = new BgmList(bgmListPK, buyItem, playlist);

            // BgmList 엔티티를 저장소에 저장합니다.
            bgmListRepository.save(bgmList);

            // 저장 성공 메시지를 로그에 기록합니다.
            log.info("BGM이 플레이리스트에 성공적으로 저장되었습니다.");
        } else {
            // 카테고리가 일치하지 않을 경우 로그 메시지를 기록합니다.
            log.info("구매 항목 카테고리가 음악이 아닙니다. BGM이 플레이리스트에 추가되지 않았습니다.");
        }
    }

    @Override
    public List<BgmListDto> getBgmList(Long playlistId) {

        List<BgmList> list = bgmListRepository.findAllByPlaylist_PlaylistId(playlistId);
        List<BgmListDto> listDto = new ArrayList<>();

        for (BgmList bgmList : list) {
            String fileUrl = s3Util
                    .getUrl(bgmList.getBuyItem().getShop().getItemFile().getFileName());
            BgmListDto bgmListDto = BgmListDto.builder()
                    .itemId(bgmList.getBuyItem().getShop().getItemId())
                    .playlistId(bgmList.getPlaylist().getPlaylistId())
                    .url(fileUrl)
                    .title(bgmList.getBuyItem().getShop().getItemName())
                    .album(s3Util.getUrl(bgmList.getBuyItem().getShop().getItemFile().getThumbnail()))
                    .artist(bgmList.getBuyItem().getShop().getUserDo().getNickname())
                    .build();
            listDto.add(bgmListDto);
        }

        return listDto;
    }

    @Override
    public void deleteBgm(Long playlistId, Long itemId) {

        Playlist playlist = playlistRepository.getReferenceById(playlistId);

        BuyItem buyItem = buyItemRepository.getReferenceById(itemId);

        BgmListPK bgmListPK = new BgmListPK(playlistId, itemId);

        BgmList bgmList = new BgmList(bgmListPK, buyItem, playlist);

        bgmListRepository.delete(bgmList);
    }

}
