package com.example.wandukong.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.wandukong.dto.MiniHomeDto;
import com.example.wandukong.service.MiniHomeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/minihome")
@RestController
public class MiniHomeController {

    @Autowired
    MiniHomeService miniHomeService;

    @GetMapping("/info")
    public ResponseEntity<?> getMiniHome(@RequestParam Long userID) {

        MiniHomeDto miniHomeDto = miniHomeService.getMiniHome(userID);
        return new ResponseEntity<>(miniHomeDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }

}
