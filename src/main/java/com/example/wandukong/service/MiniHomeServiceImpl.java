package com.example.wandukong.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.wandukong.domain.MiniHome;
import com.example.wandukong.dto.MiniHomeDto;
import com.example.wandukong.repository.MiniHomeRepository;

@Service
public class MiniHomeServiceImpl implements MiniHomeService {

    @Autowired
    MiniHomeRepository miniHomeRepository;

    @Override
    public MiniHomeDto getMiniHome(Long userID) {

        // MiniHome miniHome = miniHomeRepository.findByUserDo_UserID(userID);

        MiniHomeDto miniHomeDto = new MiniHomeDto();

        // miniHomeDto.setUserID(miniHome.getUserDo().getUserID());

        // TODO Auto-generated method stub
        return miniHomeDto;
    }

}
