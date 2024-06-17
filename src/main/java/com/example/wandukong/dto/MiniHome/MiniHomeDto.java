package com.example.wandukong.dto.MiniHome;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MiniHomeDto {

    private Long hpId;
    private Long userId;
    private String statusM;
    private String introduction;
    private int hpToday;
    private int allVisit;
    private int hpOpen;
    private Long playlistId;
    private Boolean like;
    private Long likeCount;

}
