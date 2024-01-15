package site.jigume.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.board.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select b from Board b join fetch b.goods where b.id =:boardId")
    Optional<Board> findBoardByBoardId(@Param("boardId") Long boardId);

    Optional<Board> findBoardByGoodsId(Long goodsId);

    @Modifying
    @Query("update Board b set b.isDelete = true where b.goods.id = :goodsId")
    void deleteBoard(@Param("goodsId") Long goodsId);
}