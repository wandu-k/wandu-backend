package com.example.wandukong.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.wandukong.domain.UserDo;

public interface UserRepository extends JpaRepository<UserDo, Long>, UserRepositoryCustom {

    UserDo findByEmail(String username);

}