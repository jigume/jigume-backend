package com.jigume.controller;

import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.order.SellHistoryDto;
import com.jigume.entity.goods.GoodsStatus;
import com.jigume.service.order.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SellController {

    private final SellService sellService;

    @GetMapping("/sell/{status}")
    public ResponseEntity getSellHistory(@PathVariable("status") Integer status) {
        if(status.equals(GoodsStatus.END.getStatus())) {
            SellHistoryDto sellHistoryDto = sellService.getSellEndHistory();
            return new ResponseEntity(sellHistoryDto, OK);
        }

        SellHistoryDto sellHistoryDto = sellService.getSellProcessingHistory();

        return new ResponseEntity(sellHistoryDto, OK);
    }
}
