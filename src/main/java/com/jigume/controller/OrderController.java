package com.jigume.controller;

import com.jigume.dto.goods.GoodsDto;
import com.jigume.dto.order.EndHistoryDto;
import com.jigume.dto.order.OrderDto;
import com.jigume.entity.goods.GoodsStatus;
import com.jigume.service.goods.GoodsService;
import com.jigume.service.order.OrderService;
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
        if(status == GoodsStatus.END.getStatus()) {
            List<EndHistoryDto> orderEndHistory = orderService.getOrderEndHistory();

            return new ResponseEntity(orderEndHistory, OK);
        }

        List<GoodsDto> orderProcessingHistory = orderService.getOrderProcessingHistory();

        return new ResponseEntity(orderProcessingHistory, OK);
    }
}
