package site.jigume.domain.member.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import site.jigume.domain.member.entity.WishList;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @EntityGraph(attributePaths = "goods")
    List<WishList> findWishListByMemberId(Long memberId);

    void deleteWishListByGoods_IdAndMemberId(Long goodsId, Long memberId);
}