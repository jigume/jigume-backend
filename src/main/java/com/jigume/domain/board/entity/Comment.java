package com.jigume.domain.board.entity;

import com.jigume.domain.member.entity.Member;
import com.jigume.global.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Comment")
@NoArgsConstructor
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String content;


    public static Comment createComment(Member member, Board board,
                                        String content) {
        Comment comment = new Comment();
        comment.member = member;
        comment.board = board;
        comment.content = content;

        return comment;
    }


}
