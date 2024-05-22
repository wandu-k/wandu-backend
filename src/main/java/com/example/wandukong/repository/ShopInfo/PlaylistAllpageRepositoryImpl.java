package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.QBgmList;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PlaylistAllpageRepositoryImpl implements PlaylistAllpageRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto) {
    QBgmList bgmList = QBgmList.bgmList;

    JPAQuery<BgmList> query = new JPAQuery<>(entityManager);

    // Querydsl을 사용하여 필요한 정보를 한 번에 가져옴
    List<BgmList> bgmLists = query.select(bgmList)
        .from(bgmList)
        .leftJoin(bgmList.playlist).fetchJoin()
        .leftJoin(bgmList.buyItem).fetchJoin()
        .where(bgmList.bgmListId.lt(sliceRequestDto.getLastId()))
        .limit(sliceRequestDto.getLimit())
        .fetch();

    return bgmLists;
  }
}