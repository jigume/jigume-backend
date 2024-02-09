package site.jigume.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.jigume.domain.member.dto.wish.WishListDto;
import site.jigume.domain.member.service.WishListService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wish")
public class WishListController {

    private final WishListService wishListService;

    @PostMapping("/{goodsId}")
    public ResponseEntity saveWish(@PathVariable("goodsId") Long goodsId) {
        Long wishId = wishListService.save(goodsId);

        return new ResponseEntity(wishId, CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity getWishList() {
        List<WishListDto> wishList = wishListService.getWishList();

        return new ResponseEntity(wishList, OK);
    }

    @DeleteMapping("/{goodsId}")
    public ResponseEntity deleteWish(@PathVariable("goodsId") Long goodsId) {
        wishListService.delete(goodsId);

        return new ResponseEntity(OK);
    }
}
