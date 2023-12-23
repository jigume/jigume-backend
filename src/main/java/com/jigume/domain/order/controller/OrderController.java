package com.jigume.domain.order.controller;

import com.jigume.domain.order.dto.OrderDto;
import com.jigume.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

//    @GetMapping("/orders/{status}")
//    public ResponseEntity getOrderHistory(@PathVariable("status") Integer status) {
//        if(status.equals(GoodsStatus.END.getStatus())) {
//            List<EndBuyHistoryDto> orderEndHistory = orderService.getOrderEndHistory();
//
//            return new ResponseEntity(orderEndHistory, OK);
//        }
//
//        //TODO: 진행 중인 주문 내역을 누르면 보여줄 걸 다시 생각 GoodsId만 알려주면 될듯
//        List<GoodsListDto> orderProcessingHistory = orderService.getOrderProcessingHistory();
//
//        return new ResponseEntity(orderProcessingHistory, OK);
//    }
}
