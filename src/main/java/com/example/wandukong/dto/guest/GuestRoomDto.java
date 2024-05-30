package com.example.wandukong.dto.guest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GuestRoomDto {

    private Long roomId;

    private Long userId;

    private String mainContent;

    private LocalDate writeDate;
}
