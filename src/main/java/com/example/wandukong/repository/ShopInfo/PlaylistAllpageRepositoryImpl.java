package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.QBgmList;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.querydsl.jpa.JPQLQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class PlaylistAllpageRepositoryImpl extends QuerydslRepositorySupport implements PlaylistAllpageRepository {

  public PlaylistAllpageRepositoryImpl() {
    super(BgmList.class);
  }

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Page<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto, Long userId) {
    QBgmList bgmList = QBgmList.bgmList;

    JPQLQuery<BgmList> query = from(bgmList);

    query.where(bgmList.playlist.userDo.userId.eq(userId))
        .leftJoin(bgmList.playlist).fetchJoin()
        .leftJoin(bgmList.buyItem).fetchJoin();

    // Querydsl을 사용하여 필요한 정보를 한 번에 가져옴
    List<BgmList> bgmLists = query.offset(sliceRequestDto.getOffset())
        .where(bgmList.bgmListId.lt(sliceRequestDto.getLastId()))
        .limit(sliceRequestDto.getLimit())
        .fetch();

    long total = query.fetchCount();

    return new PageImpl<>(bgmLists, sliceRequestDto.of(), total);
  }

  @Override
  public Page<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto, Long lastId,
      Long userId) {
    QBgmList bgmList = QBgmList.bgmList;

    JPQLQuery<BgmList> query = from(bgmList);

    query.where(bgmList.playlist.userDo.userId.eq(userId))
        .leftJoin(bgmList.playlist).fetchJoin()
        .leftJoin(bgmList.buyItem).fetchJoin();

    List<BgmList> bgmLists = query.select(bgmList)
        .where(bgmList.bgmListId.lt(lastId)
            .and(bgmList.playlist.userDo.userId.eq(userId)))
        .limit(sliceRequestDto.getLimit())
        .fetch();

    return new PageImpl<>(bgmLists, sliceRequestDto.of(), bgmLists.size());
  }
}