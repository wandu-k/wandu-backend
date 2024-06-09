package com.example.wandukong.dto.MiniHome;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MiniHomeDto {

    private Long hpId;
    private Long userId;
    private String statusM;
    private String introduction;
    private int hpToday;
    private int allVisit;
    private int hpOpen;
    private Long playlistId;

    @Builder
    public MiniHomeDto(Long hpId, Long userId, String statusM, String introduction, int hpToday, int allVisit,
            int hpOpen, Long playlistId) {
        this.hpId = hpId;
        this.userId = userId;
        this.statusM = statusM;
        this.introduction = introduction;
        this.hpToday = hpToday;
        this.allVisit = allVisit;
        this.hpOpen = hpOpen;
        this.playlistId = playlistId;
    }

}
