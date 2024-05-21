package com.example.wandukong.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.wandukong.domain.UserDo;

@Repository
public interface UserRepository extends JpaRepository<UserDo, Long>, UserRepositoryCustom {

    UserDo findByEmail(String username);

}