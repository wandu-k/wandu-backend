package com.example.wandukong.repository.guest;

import com.example.wandukong.domain.guest.GuestRoom;
import com.example.wandukong.repository.guest.search.GuestRoomSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRoomRepository extends JpaRepository<GuestRoom, Long>, GuestRoomSearch {
}
