package com.example.wandukong.dto;

import com.example.wandukong.domain.MiniHome;
import com.example.wandukong.domain.UserDo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MiniHomeDto {

    private Long userID;
    private String statusM;
    private String introduction;
    private int hpToday;
    private int allVisit;
    private int hpOpen;

    /* DTO -> Entity */
    public MiniHome toEntity() {
        MiniHome miniHome = MiniHome.builder()
                .userDo(UserDo.builder().userID(userID).build())
                .statusM(statusM)
                .introduction(introduction)
                .hpToday(hpToday)
                .allVisit(allVisit)
                .hpOpen(hpOpen)
                .build();
        return miniHome;
    }
}
