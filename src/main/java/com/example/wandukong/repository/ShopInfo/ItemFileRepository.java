package com.example.wandukong.repository.ShopInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.ShopInfo.ItemFile;

@Repository
public interface ItemFileRepository extends JpaRepository<ItemFile, Long> {

}