package com.example.wandukong.repository.miniHome;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.MiniHome.MiniHomePost;
import com.example.wandukong.repository.miniHome.search.MiniHomePostSearchRepo;

public interface MiniHomePostRepository extends JpaRepository<MiniHomePost, Long>, MiniHomePostSearchRepo {

}
