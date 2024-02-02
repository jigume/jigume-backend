package site.jigume.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.global.audit.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
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

    private String title;

    private String content;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private List<Comment> commentList = new ArrayList<>();

    private boolean isDelete;

    public static Board createBoard(String boardContent,Goods goods) {
        Board board = new Board();
        board.title = goods.getName();
        board.isDelete = false;
        board.content = boardContent;
        board.goods = goods;

        return board;
    }

    public void addComment(Comment comment) {
        commentList.add(comment);
    }

    public void updateBoardContent(String boardContent) {
        this.content = boardContent;
    }
}
