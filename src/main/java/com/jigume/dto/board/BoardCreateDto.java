package com.jigume.dto.board;

import lombok.Data;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.goods.entity.Goods;

@Data
public record BoardCreateDto(String content) {
    public Board toBoard(Goods goods) {
        return Board.createBoard(content, goods);
    }
}
