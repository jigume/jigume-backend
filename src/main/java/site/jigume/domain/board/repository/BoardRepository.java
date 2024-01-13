package site.jigume.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.board.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b where b.id =:boardId")
    Optional<Board> findBoardByBoardId(@Param("boardId") Long boardId);

    Optional<Board> findBoardByGoodsId(Long goodsId);
}