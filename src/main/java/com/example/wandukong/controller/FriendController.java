package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;
import com.example.wandukong.dto.FriendDto;
import com.example.wandukong.service.FriendService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user/friend")
public class FriendController {

    @Autowired
    FriendService friendService;

    @GetMapping
    public ResponseEntity<?> getFriendList() {

        List<FriendDto> friends = friendService.getFriendList();

        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addFriend(@RequestBody FriendDto friendDto) {

        friendService.addFriend(friendDto);

        return new ResponseEntity<>("팔로우 성공", HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteFriend(@RequestBody FriendDto friendDto) {
        friendService.deleteFriend(friendDto);

        return new ResponseEntity<>("팔로우 취소 성공", HttpStatus.OK);

    }

}
