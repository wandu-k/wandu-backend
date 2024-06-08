package com.example.wandukong.repository.ShopInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.BgmList;
import com.example.wandukong.dto.ShopInfo.ShopInfoDto;

@Repository
public interface BgmListRepository extends JpaRepository<BgmList, Long>, BgmListRepositoryCustom {

}
