package site.jigume.domain.board.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.jigume.domain.member.entity.Member;
import site.jigume.global.audit.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;

    private boolean isDelete;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();


    public static Comment createComment(Member member, Board board,
                                        String content) {
        Comment comment = new Comment();
        comment.member = member;
        comment.isDelete = false;
        comment.board = board;
        comment.content = content;

        return comment;
    }


    public static Comment createReplyComment(Member member, Board board,
                                             Comment parent, String content) {
        Comment comment = new Comment();
        comment.member = member;
        comment.board = board;
        comment.content = content;
        comment.isDelete = false;

        comment.parent = parent;
        parent.getChildren().add(comment);

        return comment;
    }

    public void updateContent(String commentContent) {
        this.content = commentContent;
    }

    public void deleteComment() {
        this.isDelete = true;
    }
}
