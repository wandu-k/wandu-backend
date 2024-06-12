package com.example.wandukong.repository.diary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.wandukong.domain.diary.Diary;

public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

    Optional<Diary> findByUserDo_UserIdAndPostId(Long userId, Long postId);

}
