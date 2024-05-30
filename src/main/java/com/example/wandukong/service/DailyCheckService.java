package com.example.wandukong.service;

import java.time.LocalDate;
import java.util.List;

import com.example.wandukong.dto.DailyDto;

public interface DailyCheckService {

    void dailyCheck(DailyDto dailyDto);

    List<DailyDto> getdailyCheckList(LocalDate date);

}
