package com.example.wandukong.dto.ScrollDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SliceRequestDto {

  private Long lastId;
  private int limit;

}
