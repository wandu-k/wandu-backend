package com.example.wandukong.repository.guest;

import com.example.wandukong.domain.guest.GuestComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestCommentRepository extends JpaRepository<GuestComment, Long> {
}
