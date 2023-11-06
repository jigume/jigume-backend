package com.jigume.domain.board.entity;

import com.jigume.domain.goods.entity.Goods;
import com.jigume.global.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_id")
    private Goods goods;

    private String boardName;

    private String boardContent;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    public static Board createBoard(String boardContent,Goods goods) {
        Board board = new Board();
        board.boardName = goods.getName();
        board.boardContent = boardContent;
        board.goods = goods;

        return board;
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }
}
