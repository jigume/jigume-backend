package com.jigume.domain.order.controller;

import com.jigume.domain.goods.dto.GoodsDto;
import com.jigume.domain.goods.entity.GoodsStatus;
import com.jigume.domain.order.dto.EndBuyHistoryDto;
import com.jigume.domain.order.dto.OrderDto;
import com.jigume.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{goodsId}/new")
    public ResponseEntity newOrder(@RequestBody OrderDto orderDto) {
        orderService.orderGoods(orderDto);

        return new ResponseEntity("주문이 성공적으로 되었습니다.", OK);
    }

    @GetMapping("/orders/{status}")
    public ResponseEntity getOrderHistory(@PathVariable("status") Integer status) {
        if(status.equals(GoodsStatus.END.getStatus())) {
            List<EndBuyHistoryDto> orderEndHistory = orderService.getOrderEndHistory();

            return new ResponseEntity(orderEndHistory, OK);
        }

        List<GoodsDto> orderProcessingHistory = orderService.getOrderProcessingHistory();

        return new ResponseEntity(orderProcessingHistory, OK);
    }
}
