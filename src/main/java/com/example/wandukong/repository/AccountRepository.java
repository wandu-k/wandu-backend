package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.wandukong.domain.UserDo;

@Repository
public interface AccountRepository extends JpaRepository<UserDo, Long> {

    @EntityGraph(attributePaths = { "miniHome" })
    UserDo findByEmail(String username);

}