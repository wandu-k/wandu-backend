package com.example.wandukong.dto.MiniHome;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MiniHomeDto {

    private Long hpID;
    private Long userID;
    private String statusM;
    private String introduction;
    private int hpToday;
    private int allVisit;
    private int hpOpen;

    @Builder
    public MiniHomeDto(Long hpID, Long userID, String statusM, String introduction, int hpToday, int allVisit,
            int hpOpen) {
        this.hpID = hpID;
        this.userID = userID;
        this.statusM = statusM;
        this.introduction = introduction;
        this.hpToday = hpToday;
        this.allVisit = allVisit;
        this.hpOpen = hpOpen;
    }

}
