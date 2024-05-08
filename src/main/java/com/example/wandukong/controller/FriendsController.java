package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user/api/friend")
public class FriendsController {

    @GetMapping
    public ResponseEntity<?> getFriendList(@RequestParam String param) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
