package site.jigume.domain.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import site.jigume.domain.board.entity.Board;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Modifying
    @Query("update Board b set b.isDelete = true where b.id = :boardId")
    void deleteBoard(@Param("boardId") Long boardId);
}