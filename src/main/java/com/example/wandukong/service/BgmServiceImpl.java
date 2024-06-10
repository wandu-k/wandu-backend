package com.example.wandukong.service;

import java.io.IOException;
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
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

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
    public List<BgmListDto> getBgmList(Long playlistId) {

        List<BgmList> list = bgmListRepository.findAllByBgmListId_Playlist_PlaylistId(playlistId);

        List<BgmListDto> listDto = new ArrayList<>();

        for (BgmList bgmList : list) {

            String url = s3Util.getUrl(bgmList.getBgmListId().getBuyItem().getShop().getItemFile().getFileName());

            BgmListDto.BgmListDtoBuilder bgmListDtoBuilder = BgmListDto.builder()
                    .itemId(bgmList.getBgmListId().getBuyItem().getItemId())
                    .playlistId(bgmList.getBgmListId().getPlaylist().getPlaylistId());

            try {
                Mp3File mp3file = new Mp3File(url);
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                bgmListDtoBuilder
                        .album(id3v1Tag.getAlbum())
                        .artist(id3v1Tag.getArtist())
                        .title(id3v1Tag.getTitle());

            } catch (UnsupportedTagException | InvalidDataException | IOException e) {
                e.printStackTrace();
            }

            BgmListDto bgmListDto = bgmListDtoBuilder.build();
            listDto.add(bgmListDto);
        }

        return listDto;
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
