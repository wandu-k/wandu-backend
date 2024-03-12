package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.UserDo;

@Repository
public interface AccountRepository extends JpaRepository<UserDo, Long> {

    UserDo findByEmail(String email);

}