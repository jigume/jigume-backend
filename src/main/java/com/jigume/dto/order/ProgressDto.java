package com.jigume.dto.order;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProgressDto {

    private Long boardId;

    private Integer currentMemberCount;

    private String hostNickname;


    public static ProgressDto toProgressDto(Long boardId, Integer currentMemberCount, String hostNickname) {
        ProgressDto progressDto = new ProgressDto();
        progressDto.boardId = boardId;
        progressDto.currentMemberCount = currentMemberCount;
        progressDto.hostNickname = hostNickname;

        return progressDto;
    }
}
