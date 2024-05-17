package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.domain.ShopInfo.QBgmList;
import com.example.wandukong.dto.ScrollDto.SliceRequestDto;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class PlaylistAllpageRepositoryImpl extends QuerydslRepositorySupport implements PlaylistAllpageRepository {

  public PlaylistAllpageRepositoryImpl() {
    super(BgmList.class);
    // TODO Auto-generated constructor stub
  }

  @Override
  public List<BgmList> findAllByBgmListsAndBuyItemAndPlaylist(SliceRequestDto sliceRequestDto) {
    QBgmList bgmList = QBgmList.bgmList;
    JPQLQuery<BgmList> query = from(bgmList);

    // Querydsl을 사용하여 필요한 정보를 한 번에 가져옴
    List<BgmList> bgmLists = query.select(bgmList)
        .leftJoin(bgmList.playlist).fetchJoin()
        .leftJoin(bgmList.buyItem).fetchJoin()

        .fetch();

    // 마지막으로 로드된 데이터의 ID를 이용하여 추가 데이터를 로드
    if (sliceRequestDto.getLastId() != null) {
      query.where(bgmList.bgmListId.lt(sliceRequestDto.getLastId()));
    }

    return query.fetch();

  }

}
