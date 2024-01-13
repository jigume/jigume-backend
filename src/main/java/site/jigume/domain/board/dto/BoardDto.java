package site.jigume.domain.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardDto {

    private String boardName;
    private String boardContent;
    private String hostName;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public BoardDto(String boardName, String boardContent, String hostName,
                    LocalDateTime created_at, LocalDateTime modified_at) {
        this.boardName = boardName;
        this.boardContent = boardContent;
        this.hostName = hostName;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }
}
