package com.jigume.service.board;

import com.jigume.dto.board.BoardDto;
import com.jigume.entity.board.Board;
import com.jigume.entity.goods.Goods;
import com.jigume.exception.global.exception.ResourceNotFoundException;
import com.jigume.repository.BoardRepository;
import com.jigume.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.jigume.exception.global.GlobalErrorCode.RESOURCE_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public void createBoard(String boardContent, Goods goods) {
        Board board = Board.createBoard(boardContent, goods);

        boardRepository.save(board);
    }

    public BoardDto getBoard(Long boardId) {
        Board board = boardRepository.findBoardByBoardIdWithGetComment(boardId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND));

        return BoardDto.toBoardDto(board);
    }
}
