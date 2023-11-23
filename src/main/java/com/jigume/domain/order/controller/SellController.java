package com.jigume.domain.order.controller;

import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.order.dto.SellHistoryDto;
import com.jigume.domain.order.service.SellService;
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

    private final SellService sellService;

//    @GetMapping("/sell/{status}")
//    public ResponseEntity getSellHistory(@PathVariable("status") Integer status) {
//        if(status.equals(GoodsStatus.END.getStatus())) {
//            SellHistoryDto sellHistoryDto = sellService.getSellEndHistory();
//            return new ResponseEntity(sellHistoryDto, OK);
//        }
//
//        //TODO: 진행 중인 판매 내역을 누르면 보여줄 걸 다시 생각 GoodsId만 알려주면 될듯
//        SellHistoryDto sellHistoryDto = sellService.getSellProcessingHistory();
//
//        return new ResponseEntity(sellHistoryDto, OK);
//    }
}
