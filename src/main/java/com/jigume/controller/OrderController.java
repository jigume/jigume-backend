package com.jigume.controller;

import com.jigume.dto.order.OrderDto;
import com.jigume.service.order.OrderService;
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

    //TODO: 판매 내역, 구매 내역 불러오기(두 개 다 진행중, 끝남 분리)

//    @GetMapping("/order/{orderStatusNum}/{orderTypeNum}")
//    public ResponseEntity getOrder(@PathVariable Integer orderTypeNum,
//                                   @PathVariable Integer orderStatusNum) {
//        if (orderStatusNum == 0) {
//            List<ProgressDto> progressOrderList = orderService.getProgressOrderList(orderTypeNum, orderStatusNum);
//            return new ResponseEntity(progressOrderList, OK);
//        } else {
//            List<DoneDto> doneOrderList = orderService.getDoneOrderList(orderTypeNum, orderStatusNum);
//            return new ResponseEntity<>(doneOrderList, OK);
//        }
//
//    }
}
