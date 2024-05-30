package com.example.wandukong.service.user;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Daily;
import com.example.wandukong.domain.UserDo;
import com.example.wandukong.dto.DailyDto;
import com.example.wandukong.repository.DailyCheckRepository;
import com.example.wandukong.service.DailyCheckService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyCheckServiceImpl implements DailyCheckService {

    private final DailyCheckRepository dailyCheckRepository;

    @Override
    public void dailyCheck(DailyDto dailyDto) {

        Daily daily = new Daily(null, UserDo.builder().userId(dailyDto.getUserId()).build(), dailyDto.getDate());

        dailyCheckRepository.save(daily);
    }

}
