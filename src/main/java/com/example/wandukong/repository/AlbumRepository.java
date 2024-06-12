package com.example.wandukong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAllByUserDo_UserId(Long userId);

}
