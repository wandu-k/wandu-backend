package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.BgmListPK;
import com.example.wandukong.domain.ShopInfo.BgmList;

@Repository
public interface BgmListRepository extends JpaRepository<BgmList, BgmListPK>, BgmListRepositoryCustom {

}
