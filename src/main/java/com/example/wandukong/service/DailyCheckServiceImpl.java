package com.example.wandukong.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.wandukong.domain.Daily;
import com.example.wandukong.dto.DailyDto;
import com.example.wandukong.repository.DailyCheckRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyCheckServiceImpl implements DailyCheckService {

    private final DailyCheckRepository dailyCheckRepository;

    @Override
    public void dailyCheck(DailyDto dailyDto) {

        Daily daily = new Daily(dailyDto.getUserId(), null, dailyDto.getDate());

        dailyCheckRepository.save(daily);
    }

    @Override
    public List<DailyDto> getdailyCheckList(LocalDate date) {
        List<Daily> dailes = dailyCheckRepository.findByDate(date);

        List<DailyDto> dailyDtos = new ArrayList<>();

        for (Daily daily : dailes) {
            DailyDto dailyDto = new DailyDto(daily.getId(), daily.getUserDo().getUserId(), daily.getDate());
            dailyDtos.add(dailyDto);
        }
        return dailyDtos;
    }

}
