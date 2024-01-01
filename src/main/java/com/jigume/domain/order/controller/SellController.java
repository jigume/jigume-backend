package com.jigume.domain.order.controller;

import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.order.dto.SellHistoryDto;
import com.jigume.domain.order.service.sell.SellCommendService;
import com.jigume.domain.order.service.sell.SellQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SellController {

    private final SellCommendService sellCommendService;
    private final SellQueryService sellQueryService;

    @GetMapping("/sell/{status}")
    public ResponseEntity getSellHistory(@PathVariable("status") GoodsStatus status) {
        SellHistoryDto sellHistoryDto = sellQueryService.getSellHistory(status);
        return new ResponseEntity(sellHistoryDto, OK);
    }
}
