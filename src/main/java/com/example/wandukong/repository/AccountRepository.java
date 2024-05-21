package com.example.wandukong.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wandukong.domain.UserDo;

@Repository
public interface AccountRepository extends JpaRepository<UserDo, Long>, AccountRepositoryCustom {

    UserDo findByEmail(String username);

}