package site.jigume.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.jigume.domain.board.dto.*;
import site.jigume.domain.board.entity.Board;
import site.jigume.domain.board.entity.Comment;
import site.jigume.domain.board.exception.exception.BoardException;
import site.jigume.domain.board.repository.BoardRepository;
import site.jigume.domain.board.repository.CommentRepository;
import site.jigume.domain.goods.entity.Goods;
import site.jigume.domain.goods.exception.GoodsException;
import site.jigume.domain.goods.repository.GoodsRepository;
import site.jigume.domain.member.entity.Member;
import site.jigume.domain.member.exception.auth.AuthException;
import site.jigume.domain.member.exception.auth.AuthExceptionCode;
import site.jigume.domain.member.service.MemberService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static site.jigume.domain.board.exception.exception.BoardExceptionCode.BOARD_NOT_FOUND;
import static site.jigume.domain.board.exception.exception.BoardExceptionCode.COMMENT_NOT_FOUND;
import static site.jigume.domain.goods.exception.GoodsExceptionCode.GOODS_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final GoodsRepository goodsRepository;

    @Transactional
    public void createComment(Long goodsId, Long boardId, CreateCommentDto commentDto) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);
        Goods goods = getGoods(goodsId);

        isOrderSell(goods, member);

        Comment comment = Comment.createComment(member, board, commentDto.getContent());
        board.addComment(comment);
        commentRepository.save(comment);
    }

    @Transactional
    public void createReplyComment(Long goodsId, Long boardId, CreateReplyComment createReplyComment) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);
        Goods goods = getGoods(goodsId);

        isOrderSell(goods, member);

        Comment parentComment = commentRepository.findById(createReplyComment.getParentCommentId())
                .orElseThrow(() -> new BoardException(COMMENT_NOT_FOUND));

        Comment comment = Comment.createReplyComment(member, board, parentComment
                , createReplyComment.getContent());

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long goodsId, Long boardId, Long commentId, UpdateCommentDto updateCommentDto) {
        Member member = memberService.getMember();
        Goods goods = getGoods(goodsId);

        String commentContent = updateCommentDto.getCommentContent();

        getBoard(boardId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BoardException(COMMENT_NOT_FOUND));

        if (!comment.getMember().equals(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }

        comment.updateContent(commentContent);
    }

    @Transactional
    public void deleteComment(Long goodsId, Long commentId) {
        Member member = memberService.getMember();
        Goods goods = getGoods(goodsId);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BoardException(COMMENT_NOT_FOUND));

        if (comment.getMember().equals(member)) {
            throw new AuthException(AuthExceptionCode.AUTHENTICATION_ERROR);
        }

        comment.delete();
    }

    @Transactional(readOnly = true)
    public GetCommentsDto getComments(Long goodsId, Long boardId, Pageable pageable) {
        Member member = memberService.getMember();
        Board board = getBoard(boardId);
        Goods goods = getGoods(goodsId);

        isOrderSell(goods, member);

        Page<Comment> commentsByBoardId = commentRepository.findCommentsByBoardId(boardId, pageable);

        int totalPages = commentsByBoardId.getTotalPages();

        List<CommentWithReplyDto> commentWithReplyDtoList = commentsByBoardId.stream()
                .filter(parent -> Optional.ofNullable(parent.getParent()).isEmpty())
                .map(parent -> {
                    List<CommentDto> childList = parent.getChildren().stream()
                            .map(child -> CommentDto.from(child))
                            .collect(Collectors.toList());
                    CommentDto parentDto = CommentDto.from(parent);
                    return new CommentWithReplyDto(parentDto, childList);
                })
                .collect(Collectors.toList());


        return new GetCommentsDto(commentWithReplyDtoList, totalPages);
    }

    private void isOrderSell(Goods goods, Member member) {
        if (!goods.isOrder(member) && !goods.isSell(member)) {
            throw new AuthException(AuthExceptionCode.NOT_AUTHORIZATION_USER);
        }
    }

    private Board getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardException(BOARD_NOT_FOUND));
        return board;
    }

    private Goods getGoods(Long goodsId) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new GoodsException(GOODS_NOT_FOUND));
        return goods;
    }
}
