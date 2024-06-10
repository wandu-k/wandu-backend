package com.example.wandukong.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.example.wandukong.domain.BgmListPK;
import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.BuyItem;
import com.example.wandukong.domain.ShopInfo.Playlist;
import com.example.wandukong.dto.ShopInfo.BgmListDto;
import com.example.wandukong.repository.ShopInfo.BgmListRepository;
import com.example.wandukong.repository.ShopInfo.BuyItemRepository;
import com.example.wandukong.repository.ShopInfo.PlaylistRepository;
import com.example.wandukong.util.S3Util;
import com.mpatric.mp3agic.ID3v2;
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
            String fileUrl = s3Util.getUrl(bgmList.getBgmListId().getBuyItem().getShop().getItemFile().getFileName());
            BgmListDto.BgmListDtoBuilder bgmListDtoBuilder = BgmListDto.builder()
                    .itemId(bgmList.getBgmListId().getBuyItem().getItemId())
                    .playlistId(bgmList.getBgmListId().getPlaylist().getPlaylistId()).url(fileUrl);

            try {
                URL url = new URL(fileUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() != 200) {
                    throw new IOException("Failed to fetch file: " + connection.getResponseMessage());
                }

                InputStream inputStream = connection.getInputStream();
                File tempFile = File.createTempFile("temp", ".mp3");
                FileUtils.copyInputStreamToFile(inputStream, tempFile);

                // mp3agic을 사용하여 MP3 파일 읽기
                Mp3File mp3File = new Mp3File(tempFile.getAbsolutePath());

                if (mp3File.hasId3v2Tag()) {
                    ID3v2 id3v2Tag = mp3File.getId3v2Tag();
                    String title = id3v2Tag.getTitle();
                    String artist = id3v2Tag.getArtist();
                    String album = id3v2Tag.getAlbum();
                    byte[] albumImage = id3v2Tag.getAlbumImage();

                    // 메타데이터 출력 및 DTO 설정
                    System.out.println("Title: " + title);
                    System.out.println("Artist: " + artist);
                    System.out.println("Album: " + album);

                    // 앨범 아트가 존재하는 경우
                    if (albumImage != null) {
                        // 앨범 아트를 저장하거나 사용하십시오.
                        String albumImageBase64 = Base64.getEncoder().encodeToString(albumImage);
                        bgmListDtoBuilder
                                .album(albumImageBase64);
                    } else {
                        System.out.println("No album art found");
                    }

                    bgmListDtoBuilder
                            .artist(artist)
                            .title(title);

                } else {
                    System.out.println("No ID3v2 tag found");
                }

                inputStream.close();
                connection.disconnect();
                tempFile.deleteOnExit();
            } catch (IOException | UnsupportedTagException | InvalidDataException e) {
                e.printStackTrace();
                continue;
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
