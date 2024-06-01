package com.example.wandukong.dto.ask;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AskFileDto {

    private Long askId;

    private String fileName;

    @Builder
    public AskFileDto(Long askId, String fileName) {
        this.askId = askId;
        this.fileName = fileName;
    }
}
